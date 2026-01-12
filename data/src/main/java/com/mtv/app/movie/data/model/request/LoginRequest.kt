package com.mtv.app.movie.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val name: String,
    val password: String
)