package com.mtv.app.movie.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val deviceId: String,
    val email: String,
    val password: String,
    val createdAt: String
)