package com.mtv.app.movie.domain.usecase

import com.mtv.app.movie.data.datasource.remote.FirebaseApi
import com.mtv.app.movie.domain.model.RegisterRequest
import com.mtv.app.movie.domain.model.RegisterResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.repository.NetworkRepository
import com.mtv.based.core.network.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<RegisterRequest, RegisterResponse>(dispatcher, RegisterResponse::class) {

    override suspend fun execute(param: RegisterRequest): NetworkResponse {
        return repository.request(FirebaseApi.Register)
    }

}