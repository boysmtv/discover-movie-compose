/*
 * Project: Boys.mtv@gmail.com
 * File: LogoutUseCase.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 14.53
 */

package com.mtv.app.movie.domain.usecase

import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.app.movie.data.datasource.ApiEndPoint
import com.mtv.app.movie.data.model.request.LogoutRequest
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<LogoutRequest, LogoutResponse>(dispatcher, LogoutResponse::class) {

    override suspend fun execute(param: LogoutRequest): NetworkResponse {
        return repository.request(ApiEndPoint.AuthLogout, param)
    }

}
