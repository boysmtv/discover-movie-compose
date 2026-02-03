package com.mtv.app.movie.feature.event.profile

data class ProfileEventListener(
    val onLogout: () -> Unit = {}
)