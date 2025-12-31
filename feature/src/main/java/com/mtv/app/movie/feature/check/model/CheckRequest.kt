package com.mtv.app.movie.feature.check.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckRequest(
    val username: String,
)