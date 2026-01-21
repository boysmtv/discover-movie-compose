package com.mtv.app.movie.feature.event.login

data class LoginEventListener(
    val onLoginClicked: (username: String, password: String) -> Unit
)