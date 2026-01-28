package com.mtv.app.movie.feature.event.profile

data class ProfileNavigationListener(
    val onNavigateToSettings: () -> Unit = {}
)