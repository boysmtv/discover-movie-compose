package com.mtv.app.movie.domain.movie

import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.app.movie.data.datasource.TmdbApi
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.data.model.movie.MovieVideoResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MoviesVideosUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Int, MovieVideoResponse>(dispatcher) {

    override suspend fun execute(param: Int) = repository.request<MovieVideoResponse>(
        endpoint = TmdbApi.GetDetailVideos(param)
    )

}