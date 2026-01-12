package com.mtv.app.movie.domain.usecase

import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.app.movie.data.datasource.ApiEndPoint
import com.mtv.app.movie.data.model.request.CheckRequest
import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CheckUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<CheckRequest, CheckResponse>(dispatcher, CheckResponse::class) {

    override suspend fun execute(param: CheckRequest): NetworkResponse {
        return repository.request(ApiEndPoint.AuthCheck, param)
    }

}