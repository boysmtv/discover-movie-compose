package com.mtv.app.movie.domain.usecase

import com.mtv.app.movie.domain.model.RegisterRequest
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.datasource.FirebaseDataSource
import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.firebase.usecase.BaseFirebaseUseCase
import com.mtv.based.core.network.firebase.utils.FirebaseUiError
import com.mtv.based.core.network.utils.ErrorMessages
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

    override fun execute(param: RegisterRequest): Flow<FirebaseResult<String>> = flow {
        val exists = dataSource.isExistByFields(
            collection = config.defaultCollection,
            data = mapOf(
                "email" to param.email,
                "phone" to param.phone
            )
        ).first()

        if (exists) {
            emit(FirebaseResult.Error(FirebaseUiError.Permission(ErrorMessages.USER_ALREADY_EXISTS)))
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