package com.mtv.app.movie.data.model.movie

import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("page")
    val page: Int = 0,

    @SerialName("results")
    val results: List<MoviesItemResponse> = emptyList(),

    @SerialName("total_pages")
    val totalPages: Int = 0,

    @SerialName("total_results")
    val totalResults: Int = 0
)

@Serializable
data class MoviesItemResponse(
    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("id")
    val id: Int = 0,

    @SerialName("original_language")
    val originalLanguage: String = EMPTY_STRING,

    @SerialName("original_title")
    val originalTitle: String = EMPTY_STRING,

    @SerialName("overview")
    val overview: String = EMPTY_STRING,

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String = EMPTY_STRING,

    @SerialName("title")
    val title: String = EMPTY_STRING,

    @SerialName("video")
    val video: Boolean = false,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0
)