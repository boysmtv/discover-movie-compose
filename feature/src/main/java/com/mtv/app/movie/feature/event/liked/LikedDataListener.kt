package com.mtv.app.movie.feature.event.liked

import com.mtv.app.movie.data.model.movie.MovieDetailResponse

data class LikedDataListener(
    val movieLikedList: List<MovieDetailResponse> = emptyList(),
)
