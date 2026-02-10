/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SearchContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 13.48
 */

package com.mtv.app.movie.feature.contract

import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.based.core.network.utils.Resource

data class SearchStateListener(
    val searchState: Resource<MoviesResponse> = Resource.Loading,
    val upComingState: Resource<MoviesResponse> = Resource.Loading
)

data class SearchDataListener(
    val query: String = "",
    val movies: List<MoviesItemResponse> = emptyList()
)

data class SearchEventListener(
    val onDoSearch: (String) -> Unit
)

data class SearchNavigationListener(
    val onNavigateToMovieDetail: (movieId: Int) -> Unit,
)