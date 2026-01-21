package com.mtv.app.movie.feature.event.register

data class RegisterEventListener(
    val onRegisterClicked: (name: String, email: String, phone: String, password: String) -> Unit
)