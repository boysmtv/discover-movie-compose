package com.mtv.app.movie.domain.user

import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.core.provider.utils.safeToDataClass
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.UiErrorFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LoginUseCase<T : Any> @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    private val clazz: Class<T>,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<LoginRequest, T>(dispatcher) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(param: LoginRequest): Flow<ResourceFirebase<T>> {
        return insertLoginLog(param)
            .flatMapLatest { logResult ->
                when (logResult) {

                    is ResourceFirebase.Loading -> flowOf(ResourceFirebase.Loading)

                    is ResourceFirebase.Error -> flowOf(ResourceFirebase.Error(logResult.error))

                    is ResourceFirebase.Success -> getUserData(param)
                }
            }
            .catch {
                emit(
                    ResourceFirebase.Error(
                        UiErrorFirebase.Unknown(ErrorMessages.SERVER_ERROR)
                    )
                )
            }
    }

    private fun insertLoginLog(
        param: LoginRequest
    ): Flow<ResourceFirebase<String>> {
        return dataSource.addDocument(
            collection = BuildConfig.FIREBASE_LOGIN_LOG_COLLECTION,
            data = param.toMap()
        )
    }

    private fun getUserData(
        param: LoginRequest
    ): Flow<ResourceFirebase<T>> {
        return dataSource.getDocumentByFields(
            collection = config.defaultCollection,
            data = mapOf(
                "email" to param.email,
                "password" to param.password
            ),
            mapper = { it.safeToDataClass(clazz) }
        )
    }
}
