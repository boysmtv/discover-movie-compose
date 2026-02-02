package com.mtv.app.movie.feature.event.liked

data class LikedNavigationListener(
    val onNavigateToMovieDetail: (movieId: Int) -> Unit,
)