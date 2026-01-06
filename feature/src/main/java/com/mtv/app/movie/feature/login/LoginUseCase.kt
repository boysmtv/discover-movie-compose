package com.mtv.app.movie.feature.login

import com.mtv.app.movie.data.datasource.remote.ApiEndPoint
import com.mtv.app.movie.feature.login.model.LoginRequest
import com.mtv.app.movie.feature.login.model.LoginResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.repository.NetworkRepository
import com.mtv.based.core.network.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginRequest, LoginResponse>(dispatcher, LoginResponse::class) {

    override suspend fun execute(param: LoginRequest): NetworkResponse {
        return repository.request(ApiEndPoint.AuthLogin, param)
    }

}