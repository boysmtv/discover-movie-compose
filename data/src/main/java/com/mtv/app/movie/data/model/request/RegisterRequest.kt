package com.mtv.app.movie.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val deviceId: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val createdAt: String
)