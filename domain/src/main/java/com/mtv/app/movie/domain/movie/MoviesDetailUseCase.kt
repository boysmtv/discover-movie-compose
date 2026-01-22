package com.mtv.app.movie.domain.movie

import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.app.movie.data.datasource.TmdbApi
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MoviesDetailUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<Int, MovieDetailResponse>(dispatcher) {

    override suspend fun execute(param: Int) = repository.request<MovieDetailResponse>(
        endpoint = TmdbApi.GetDetailMovies(param)
    )

}