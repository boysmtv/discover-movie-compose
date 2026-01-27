package com.mtv.app.movie.domain.user

import com.google.firebase.auth.FirebaseAuth
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.safeToDataClass
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.common.login.LoginStatus
import com.mtv.app.movie.common.login.mapFirebaseExceptionToUiError
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginUseCase<T : Any> @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    private val clazz: Class<T>,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<LoginRequest, T>(dispatcher) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(param: LoginRequest): Flow<ResourceFirebase<T>> =
        insertLoginLog(param)
            .flatMapLatest { logResult ->
                when (logResult) {
                    is ResourceFirebase.Loading -> flowOf(ResourceFirebase.Loading)
                    is ResourceFirebase.Error -> flowOf(ResourceFirebase.Error(logResult.error))
                    is ResourceFirebase.Success -> authenticateAndLoadUser(param, logResult.data)
                }
            }

    private fun authenticateAndLoadUser(param: LoginRequest, logId: String): Flow<ResourceFirebase<T>> = flow {
        emit(ResourceFirebase.Loading)
        try {
            val uid = firebaseAuth.signInWithEmailAndPassword(param.email, param.password)
                .await()
                .user
                ?.uid
                ?: throw IllegalStateException(ErrorMessages.NOT_FOUND)

            updateLoginStatus(logId, LoginStatus.SUCCESS)
            emitAll(getUserByUid(uid))
        } catch (e: Throwable) {
            updateLoginStatus(logId, LoginStatus.FAILED)
            emit(ResourceFirebase.Error(mapFirebaseExceptionToUiError(e)))
        }
    }

    private fun insertLoginLog(param: LoginRequest): Flow<ResourceFirebase<String>> =
        dataSource.addDocument(
            collection = BuildConfig.FIREBASE_LOGIN_LOG_COLLECTION,
            data = mapOf(
                "deviceId" to param.deviceId,
                "email" to param.email,
                "createdAt" to param.createdAt,
                "status" to LoginStatus.ATTEMPT.name
            )
        )

    private fun getUserByUid(uid: String): Flow<ResourceFirebase<T>> =
        dataSource.getDocument(
            collection = config.defaultCollection,
            documentId = uid,
            mapper = { it.safeToDataClass(clazz) }
        )

    private suspend fun updateLoginStatus(logId: String, status: LoginStatus) {
        dataSource.updateDocument(
            collection = BuildConfig.FIREBASE_LOGIN_LOG_COLLECTION,
            documentId = logId,
            data = mapOf(
                "status" to status.name,
                "updatedAt" to System.currentTimeMillis()
            )
        ).collect()
    }
}
