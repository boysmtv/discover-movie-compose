package com.mtv.app.movie.feature.event.register

data class RegisterNavigationListener(
    val onNavigateToLogin: () -> Unit,
    val onRegisterByGoogle: () -> Unit,
    val onRegisterByFacebook: () -> Unit,
)