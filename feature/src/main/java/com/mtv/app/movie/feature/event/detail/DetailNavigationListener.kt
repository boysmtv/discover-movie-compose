package com.mtv.app.movie.feature.event.detail

data class DetailNavigationListener(
    val onNavigateToBack: () -> Unit,
    val onNavigateToPlayMovie: (key: String) -> Unit
)