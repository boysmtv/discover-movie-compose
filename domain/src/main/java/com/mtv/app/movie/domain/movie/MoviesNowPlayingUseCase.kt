package com.mtv.app.movie.domain.usecase.movie

import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.app.movie.data.datasource.TmdbApi
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MoviesNowPlayingUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, MoviesResponse>(dispatcher) {

    override suspend fun execute(param: Unit) = repository.request<MoviesResponse>(
        endpoint = TmdbApi.GetNowPlaying,
        body = param
    )

}