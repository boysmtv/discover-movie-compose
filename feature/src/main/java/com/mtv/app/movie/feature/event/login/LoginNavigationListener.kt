package com.mtv.app.movie.feature.event.login

data class LoginNavigationListener(
    val onNavigateToHome: () -> Unit,
    val onNavigateToSignUpByEmail: () -> Unit,
    val onNavigateToForgotPassword: () -> Unit,
)