package com.mtv.app.movie.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequest(
    val username: String,
)