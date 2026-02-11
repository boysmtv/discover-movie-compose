package com.mtv.app.movie.nav

import com.mtv.app.movie.common.Constant

object AppDestinations {
    const val SPLASH_GRAPH = "SPLASH_GRAPH"
    const val LOGIN_GRAPH = "LOGIN_GRAPH"
    const val REGISTER_GRAPH = "REGISTER_GRAPH"
    const val HOME_GRAPH = "HOME_GRAPH"
    const val RESET_GRAPH = "FORGOT_GRAPH"
    const val NOTIFICATION_GRAPH = "NOTIFICATION_GRAPH"
    const val MOVIE_DETAIL_ROUTE = "MOVIE_DETAIL_ROUTE/{${Constant.SharedParam.MOVIE_ID}}"
    const val MOVIE_PLAY_ROUTE = "MOVIE_PLAY_ROUTE/{${Constant.SharedParam.VIDEOS_KEY}}"
    const val EDIT_PROFILE_GRAPH = "EDIT_PROFILE_GRAPH"
    const val CHANGE_PASSWORD_GRAPH = "CHANGE_PASSWORD_GRAPH"
    const val SETTING_GRAPH = "SETTING_GRAPH"

    fun navigateToDetailMovies(movieId: Int): String {
        return "MOVIE_DETAIL_ROUTE/$movieId"
    }

    fun navigateToPlayMovie(key: String): String {
        return "MOVIE_PLAY_ROUTE/$key"
    }

}