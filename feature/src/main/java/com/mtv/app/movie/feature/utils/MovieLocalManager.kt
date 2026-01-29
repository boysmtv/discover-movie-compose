package com.mtv.app.movie.feature.utils

import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.movie.common.getList
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import javax.inject.Inject

class MovieLocalManager @Inject constructor(
    private val securePrefs: SecurePrefs
) {

    fun addMovie(key: String, movie: MovieDetailResponse) {
        val movies = securePrefs.getList<MovieDetailResponse>(key)

        if (movies.none { it.id == movie.id }) {
            movies.add(movie)
            securePrefs.putObject(key, ArrayList(movies))
        }
    }

    fun getMovies(key: String): List<MovieDetailResponse> {
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

    fun removeMovie(key: String, movieId: Int) {
        val movies = securePrefs.getList<MovieDetailResponse>(key)
            .filterNot { it.id == movieId }

        securePrefs.putObject(key, ArrayList(movies))
    }

    fun isMovieSaved(key: String, movieId: Int): Boolean {
        return securePrefs.getList<MovieDetailResponse>(key)
            .any { it.id == movieId }
    }

}