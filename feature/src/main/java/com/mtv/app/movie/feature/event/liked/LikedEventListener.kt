package com.mtv.app.movie.feature.event.liked

data class LikedEventListener(
    val onLoadLikedMovies: () -> Unit
)