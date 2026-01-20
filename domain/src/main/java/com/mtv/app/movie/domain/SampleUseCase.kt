package com.mtv.app.movie.domain

import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.app.movie.data.datasource.ApiEndPoint
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SampleUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginRequest, LoginResponse>(dispatcher) {

    override suspend fun execute(param: LoginRequest) = repository.request<LoginResponse>(
        endpoint = ApiEndPoint.AuthLogin,
        body = param,
    )

}