package com.mtv.app.movie.domain.usecase

import com.mtv.app.core.provider.based.BaseFirebaseUseCase
import com.mtv.app.movie.data.model.request.RegisterRequest
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.datasource.FirebaseDataSource
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.core.network.utils.UiErrorFirebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<RegisterRequest, String>(dispatcher) {

    override fun execute(param: RegisterRequest): Flow<ResourceFirebase<String>> = flow {
        val exists = dataSource.isExistByFields(
            collection = config.defaultCollection,
            data = mapOf(
                "email" to param.email,
                "phone" to param.phone
            )
        ).first()

        if (exists) {
            emit(ResourceFirebase.Error(UiErrorFirebase.Permission(ErrorMessages.USER_ALREADY_EXISTS)))
            return@flow
        }

        dataSource.addDocument(
            collection = config.defaultCollection,
            data = mapOf(
                "name" to param.name,
                "email" to param.email,
                "phone" to param.phone,
                "password" to param.password,
                "date" to param.createdAt
            )
        ).collect { result ->
            emit(result)
        }
    }

}