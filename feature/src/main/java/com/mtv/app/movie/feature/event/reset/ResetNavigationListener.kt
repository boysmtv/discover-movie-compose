package com.mtv.app.movie.feature.event.reset

data class ResetNavigationListener(
    val onNavigateToLogin: () -> Unit,
    val onRegisterByGoogle: () -> Unit,
    val onRegisterByFacebook: () -> Unit,
)