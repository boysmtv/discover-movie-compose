package com.mtv.app.movie.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val userId: String,
    val userToken: String,
    val lastLogin: String
)