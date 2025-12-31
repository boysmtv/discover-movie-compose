package com.mtv.app.movie.feature.logout.model

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequest(
    val username: String,
)