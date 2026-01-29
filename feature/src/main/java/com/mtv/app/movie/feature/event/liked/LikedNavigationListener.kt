package com.mtv.app.movie.feature.event.liked

data class LikedNavigationListener(
    val onNavigateToHome: () -> Unit,
    val onNavigateToSignUpByEmail: () -> Unit,
    val onNavigateToSignUpByGoogle: () -> Unit,
    val onNavigateToSignUpByFacebook: () -> Unit,
    val onNavigateToForgotPassword: () -> Unit,
)