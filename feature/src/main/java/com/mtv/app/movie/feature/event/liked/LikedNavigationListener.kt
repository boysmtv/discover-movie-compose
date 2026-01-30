package com.mtv.app.movie.feature.event.liked

import com.mtv.app.movie.data.model.movie.MovieDetailResponse

data class LikedNavigationListener(
    val onNavigateToMovieDetail: (movie: MovieDetailResponse) -> Unit,
)