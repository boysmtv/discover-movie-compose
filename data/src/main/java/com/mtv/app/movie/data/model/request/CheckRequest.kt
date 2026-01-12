package com.mtv.app.movie.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckRequest(
    val email: String,
)