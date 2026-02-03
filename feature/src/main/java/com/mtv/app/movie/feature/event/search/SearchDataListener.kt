/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SearchDataListener.kt
 *
 * Last modified by Dedy Wijaya on 02/02/26 14.58
 */

package com.mtv.app.movie.feature.event.search

import com.mtv.app.movie.data.model.movie.MoviesItemResponse

data class SearchDataListener(
    val query: String = "",
    val movies: List<MoviesItemResponse> = emptyList()
)