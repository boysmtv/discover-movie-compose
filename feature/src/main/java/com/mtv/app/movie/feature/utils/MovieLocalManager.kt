package com.mtv.app.movie.feature.utils

import com.google.protobuf.LazyStringArrayList.emptyList
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.movie.common.getList
import com.mtv.app.movie.common.putList
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import javax.inject.Inject
import kotlin.collections.emptyList

class MovieLocalManager @Inject constructor(
    private val securePrefs: SecurePrefs
) {

    fun addMovie(key: String, movie: MovieDetailResponse) {
        val movies = securePrefs.getList<MovieDetailResponse>(key)
        if (movies.none { it.id == movie.id }) {
            movies.add(movie)
            securePrefs.putList(key, movies)
        }
    }

    fun getAllMovies(key: String): List<MovieDetailResponse> {
        return securePrefs.getList<MovieDetailResponse>(key)
    }

    fun getMovieById(
        key: String,
        movieId: Int
    ): MovieDetailResponse? {
        return securePrefs
            .getList<MovieDetailResponse>(key)
            .firstOrNull { it.id == movieId }
    }

    fun isMovieSaved(key: String, movieId: Int): Boolean {
        return securePrefs
            .getList<MovieDetailResponse>(key)
            .any { it.id == movieId }
    }

    fun removeMovie(key: String, movieId: Int) {
        val movies = securePrefs
            .getList<MovieDetailResponse>(key)
            .filterNot { it.id == movieId }

        securePrefs.putList(key, movies)
    }

    fun clearMovies(key: String) {
        return securePrefs.putList(key, emptyList())
    }
}
