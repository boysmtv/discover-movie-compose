package com.mtv.app.movie.feature.event.profile

data class ProfileNavigationListener(
    val onNavigateToEditProfile: () -> Unit = {},
    val onNavigateToAddPin: () -> Unit = {},
    val onNavigateToSettings: () -> Unit = {},
    val navigateToLoginAndClearBackStack: () -> Unit = {}
)