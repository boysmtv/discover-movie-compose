package com.mtv.app.movie.data.datasource

import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.utils.HttpMethod

class TmdbApi {

    companion object {
        const val API_VERSION = 3
        const val PATH_MOVIE = "movie"
        const val PATH_VIDEOS = "videos"
        const val PATH_SEARCH = "search"
    }

    object GetNowPlaying : IApiEndPoint {
        override val path = "$API_VERSION/$PATH_MOVIE/now_playing"
        override val method = HttpMethod.Get
    }

    object GetPopular : IApiEndPoint {
        override val path = "$API_VERSION/$PATH_MOVIE/popular"
        override val method = HttpMethod.Get
    }

    object GetTopRated : IApiEndPoint {
        override val path = "$API_VERSION/$PATH_MOVIE/top_rated"
        override val method = HttpMethod.Get
    }

    object GetUpComing : IApiEndPoint {
        override val path = "$API_VERSION/$PATH_MOVIE/upcoming"
        override val method = HttpMethod.Get
    }

    class GetDetailMovies(movieId: Int) : IApiEndPoint {
        override val path = "$API_VERSION/$PATH_MOVIE/$movieId"
        override val method = HttpMethod.Get
    }

    class GetDetailVideos(movieId: Int) : IApiEndPoint {
        override val path = "$API_VERSION/$PATH_MOVIE/$movieId/$PATH_VIDEOS"
        override val method = HttpMethod.Get
    }

    class SearchMovie(query: String, language: String = "en") : IApiEndPoint {
        override val path = "$API_VERSION/$PATH_SEARCH/$PATH_MOVIE?query=$query&language=$language"
        override val method = HttpMethod.Get
    }

}