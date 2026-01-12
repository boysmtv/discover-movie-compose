package com.mtv.app.movie.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val name: String,
    val email: String,
    val phone: String,
    val date: String
)