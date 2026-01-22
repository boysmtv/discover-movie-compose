package com.mtv.app.movie.feature.event.detail

data class DetailEventListener(
    val onLoadMovies: () -> Unit,
    val onPlayMovies: () -> Unit,
)