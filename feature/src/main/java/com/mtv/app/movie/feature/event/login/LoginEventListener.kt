package com.mtv.app.movie.feature.event.login

data class LoginEventListener(
    val onLoginByEmailClicked: (username: String, password: String) -> Unit,
    val onLoginByGoogleClicked: () -> Unit,
    val onLoginByFacebookClicked: () -> Unit
)