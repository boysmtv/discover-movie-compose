package com.mtv.app.movie.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val name: String,
    val password: String
)