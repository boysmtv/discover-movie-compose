package com.mtv.app.movie.feature.event.register

data class RegisterEventListener(
    val onRegisterByEmailClicked: (name: String, email: String, phone: String, password: String, photoBase64: String) -> Unit,
    val onRegisterByGoogleClicked: () -> Unit,
    val onRegisterByFacebookClicked: () -> Unit,
    val onDismissActiveDialog: () -> Unit
)