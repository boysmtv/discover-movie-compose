package com.mtv.app.movie.domain.usecase

import com.mtv.app.movie.data.model.PredictionData
import com.mtv.app.movie.data.remote.tmdb.ApiEndPoint
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.model.NetworkResponse
import com.mtv.based.core.network.repository.NetworkRepository
import com.mtv.based.core.network.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, PredictionData>(dispatcher, PredictionData::class) {

    override suspend fun execute(param: Unit): NetworkResponse {
        return repository.request(ApiEndPoint.GetName)
    }

}