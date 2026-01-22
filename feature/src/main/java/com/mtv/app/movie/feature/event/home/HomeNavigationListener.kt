package com.mtv.app.movie.feature.event.home

import com.mtv.app.movie.data.model.movie.MoviesItemResponse

data class HomeNavigationListener(
    val onNavigateToLogin: () -> Unit,
    val onNavigateToMovieDetail: (movie: MoviesItemResponse) -> Unit
)