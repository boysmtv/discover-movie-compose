package com.mtv.app.movie.feature.login

import com.mtv.app.movie.data.remote.tmdb.TmdbApi
import com.mtv.app.movie.feature.login.model.LoginRequest
import com.mtv.app.movie.feature.login.model.LoginResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.usecase.BaseUseCase
import com.mtv.based.core.network.utils.NetworkRepository
import com.mtv.based.core.network.utils.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginRequest, LoginResponse>(dispatcher, LoginResponse::class) {

    override suspend fun execute(param: LoginRequest): NetworkResponse {
        return repository.post(TmdbApi.postLogin, param)
    }

}