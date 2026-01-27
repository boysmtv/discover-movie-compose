package com.mtv.app.movie.feature.event.detail

import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.data.model.movie.MovieVideoResponse
import com.mtv.based.core.network.utils.Resource

data class DetailStateListener(
    val detailState: Resource<MovieDetailResponse> = Resource.Loading,
    val videosState: Resource<MovieVideoResponse> = Resource.Loading,
    val addMyListState: AddActionState = AddActionState.None,
    val addMyLikeState: AddActionState = AddActionState.None
)

sealed class AddActionState {
    object None : AddActionState()
    object Success : AddActionState()
    data class Error(val message: String) : AddActionState()
}