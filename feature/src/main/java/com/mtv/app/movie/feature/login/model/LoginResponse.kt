package com.mtv.app.movie.feature.login.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val firstname: String,
    val lastname: String,
    val lastLogin: String
)