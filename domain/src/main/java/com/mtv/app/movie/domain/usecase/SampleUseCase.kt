package com.mtv.app.movie.domain.usecase

import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.app.movie.data.datasource.ApiEndPoint
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SampleUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginRequest, LoginResponse>(dispatcher, LoginResponse::class) {

    override suspend fun execute(param: LoginRequest): NetworkResponse {
        return repository.request(ApiEndPoint.AuthLogin, param)
    }

}