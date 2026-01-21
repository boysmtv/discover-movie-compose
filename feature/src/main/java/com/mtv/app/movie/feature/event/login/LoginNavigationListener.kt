package com.mtv.app.movie.feature.event.login

data class LoginNavigationListener(
    val onNavigateToHome: () -> Unit,
    val onNavigateToSignUpByEmail: () -> Unit,
    val onNavigateToSignUpByGoogle: () -> Unit,
    val onNavigateToSignUpByFacebook: () -> Unit,
    val onNavigateToForgotPassword: () -> Unit,
)