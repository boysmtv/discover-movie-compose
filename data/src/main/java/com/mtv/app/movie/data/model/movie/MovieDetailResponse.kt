package com.mtv.app.movie.data.model.movie

import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieDetailResponse(
    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,

    @SerialName("budget")
    val budget: Int = 0,

    @SerialName("genres")
    val genres: List<GenreItem> = emptyList(),

    @SerialName("homepage")
    val homepage: String? = null,

    @SerialName("id")
    val id: Int = 0,

    @SerialName("imdb_id")
    val imdbId: String? = null,

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

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),

    @SerialName("release_date")
    val releaseDate: String = EMPTY_STRING,

    @SerialName("revenue")
    val revenue: Long = 0L,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList(),

    @SerialName("status")
    val status: String = EMPTY_STRING,

    @SerialName("tagline")
    val tagline: String? = null,

    @SerialName("title")
    val title: String = EMPTY_STRING,

    @SerialName("video")
    val video: Boolean = false,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0
)

@Serializable
data class BelongsToCollection(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("name")
    val name: String = EMPTY_STRING,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null
)


@Serializable
data class GenreItem(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("name")
    val name: String = EMPTY_STRING
)

@Serializable
data class ProductionCompany(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("logo_path")
    val logoPath: String? = null,

    @SerialName("name")
    val name: String = EMPTY_STRING,

    @SerialName("origin_country")
    val originCountry: String = EMPTY_STRING
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String = EMPTY_STRING,

    @SerialName("name")
    val name: String = EMPTY_STRING
)


@Serializable
data class SpokenLanguage(
    @SerialName("english_name")
    val englishName: String = EMPTY_STRING,

    @SerialName("iso_639_1")
    val iso6391: String = EMPTY_STRING,

    @SerialName("name")
    val name: String = EMPTY_STRING
)
