/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SearchMovieUseCase.kt
 *
 * Last modified by Dedy Wijaya on 02/02/26 14.57
 */

package com.mtv.app.movie.domain.movie

import com.mtv.app.core.provider.based.BaseUseCase
import com.mtv.app.movie.data.datasource.TmdbApi
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.based.core.network.di.IoDispatcher
import com.mtv.based.core.network.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: NetworkRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseUseCase<String, MoviesResponse>(dispatcher) {

    override suspend fun execute(param: String) = repository.request<MoviesResponse>(
        endpoint = TmdbApi.SearchMovie(param)
    )

}
