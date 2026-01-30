package com.mtv.app.movie.data.model.movie

import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoResponse(
    @SerialName("id")
    val movieId: Int = 0,

    @SerialName("results")
    val results: List<MovieVideoItem> = emptyList()
)

@Serializable
data class MovieVideoItem(
    @SerialName("iso_639_1")
    val iso6391: String = EMPTY_STRING,

    @SerialName("iso_3166_1")
    val iso31661: String = EMPTY_STRING,

    @SerialName("name")
    val name: String = EMPTY_STRING,

    @SerialName("key")
    val key: String = EMPTY_STRING,

    @SerialName("site")
    val site: String = EMPTY_STRING,

    @SerialName("size")
    val size: Int = 0,

    @SerialName("type")
    val type: String = EMPTY_STRING,

    @SerialName("official")
    val official: Boolean = false,

    @SerialName("published_at")
    val publishedAt: String = EMPTY_STRING,

    @SerialName("id")
    val id: String = EMPTY_STRING
)