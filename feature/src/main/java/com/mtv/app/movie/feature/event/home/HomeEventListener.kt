package com.mtv.app.movie.feature.event.home

data class HomeEventListener(
    val onCheck: (email: String) -> Unit,
    val onLogout: (email: String) -> Unit,
    val onLoadMovies: () -> Unit,
)