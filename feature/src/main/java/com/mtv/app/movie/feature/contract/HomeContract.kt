/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: HomeContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 13.45
 */

package com.mtv.app.movie.feature.contract

import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

data class HomeStateListener(
    val logoutState: ResourceFirebase<LogoutResponse> = ResourceFirebase.Loading,
    val nowPlayingState: Resource<MoviesResponse> = Resource.Loading,
    val popularState: Resource<MoviesResponse> = Resource.Loading,
    val topRatedState: Resource<MoviesResponse> = Resource.Loading,
    val upComingState: Resource<MoviesResponse> = Resource.Loading,
)

data class HomeDataListener(
    val loginResponse: LoginResponse? = null,
)

data class HomeEventListener(
    val onLogout: (email: String) -> Unit,
    val onLoadMovies: () -> Unit,
)

data class HomeNavigationListener(
    val onNavigateToLogin: () -> Unit,
    val onNavigateToMovieDetail: (movie: MoviesItemResponse) -> Unit
)