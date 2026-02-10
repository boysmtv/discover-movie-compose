package com.mtv.app.movie.feature.event.home

data class HomeEventListener(
    val onLogout: (email: String) -> Unit,
    val onLoadMovies: () -> Unit,
)