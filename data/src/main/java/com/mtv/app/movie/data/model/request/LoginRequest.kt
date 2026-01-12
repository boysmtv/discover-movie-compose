package com.mtv.app.movie.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val deviceId: String,
    val name: String,
    val password: String
)