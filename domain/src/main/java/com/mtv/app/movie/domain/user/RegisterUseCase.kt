package com.mtv.app.movie.domain.user

import com.google.firebase.auth.FirebaseAuth
import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.common.login.mapFirebaseExceptionToUiError
import com.mtv.app.movie.data.model.request.RegisterRequest
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<RegisterRequest, String>(dispatcher) {

    override fun execute(param: RegisterRequest): Flow<ResourceFirebase<String>> = flow {
        val uid = createAuthentication(param)
        val userData = param
            .copy(uid = uid)
            .toMap()

        emitAll(
            saveUserToFirestore(uid, userData)
        )
    }.catch { throwable ->
        emit(ResourceFirebase.Error(mapFirebaseExceptionToUiError(throwable)))
    }

    private suspend fun createAuthentication(
        param: RegisterRequest
    ): String {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(param.email, param.password)
            .await()
        val uid = result.user?.uid ?: throw IllegalStateException(ErrorMessages.GENERIC_ERROR)
        return uid
    }

    private fun saveUserToFirestore(
        uid: String,
        userData: Map<String, Any>
    ): Flow<ResourceFirebase<String>> =
        dataSource.setDocument(
            collection = config.defaultCollection,
            documentId = uid,
            data = userData
        ).map { result ->
            when (result) {
                is ResourceFirebase.Loading -> ResourceFirebase.Loading
                is ResourceFirebase.Error -> ResourceFirebase.Error(result.error)
                is ResourceFirebase.Success -> ResourceFirebase.Success(uid)
            }
        }

}
