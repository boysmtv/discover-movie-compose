package com.mtv.app.movie.feature.event.liked

import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.feature.event.detail.AddActionState
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

data class LikedStateListener(
    val likedMovieState: Resource<MoviesResponse> = Resource.Loading,
    val movieDeletedState: AddActionState = AddActionState.None,
)
