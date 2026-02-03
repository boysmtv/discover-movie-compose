package com.mtv.app.movie.feature.event.search

data class SearchNavigationListener(
    val onNavigateToMovieDetail: (movieId: Int) -> Unit,
)