package com.mtv.app.movie.domain.usecase

import com.mtv.app.movie.domain.model.RegisterRequest
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.datasource.FirebaseDataSource
import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.firebase.usecase.BaseFirebaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val dataSource: FirebaseDataSource,
    private val config: FirebaseConfig,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseFirebaseUseCase<RegisterRequest, String>(dispatcher) {

    override fun execute(param: RegisterRequest): Flow<FirebaseResult<String>> {
        return dataSource.addDocument(
            collection = config.defaultCollection,
            data = mapOf(
                "name" to param.name,
                "email" to param.email,
                "phone" to param.phone,
                "password" to param.password,
                "date" to param.date
            )
        )
    }

}