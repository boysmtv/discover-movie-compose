package com.mtv.app.movie.data.model.movie

import kotlinx.serialization.Serializable

@Serializable
data class MovieUi(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double,
    val genres: List<String>,
    val overview: String
)
