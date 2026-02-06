package com.mtv.app.movie.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    var uid: String,
    val deviceId: String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val photo: String,
    val createdAt: String
)