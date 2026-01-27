package com.mtv.app.movie.feature.event.reset

data class ResetEventListener(
    val onResetPasswordClicked: (email: String) -> Unit
)