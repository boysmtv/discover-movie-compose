package com.mtv.app.movie.feature.event.home

import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.app.movie.data.model.response.CheckResponse
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
