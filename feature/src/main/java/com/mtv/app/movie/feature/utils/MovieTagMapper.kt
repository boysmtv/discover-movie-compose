/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: MovieTagMapper.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.03
 */

package com.mtv.app.movie.feature.utils

import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import org.threeten.bp.LocalDate

fun MoviesItemResponse.priorityTag(
    today: LocalDate = LocalDate.now()
): MovieTag? {
    val tags = mutableListOf<MovieTag>()

    releaseDate.let {
        runCatching {
            val date = LocalDate.parse(it)

            if (date.isAfter(today)) {
                tags += MovieTag.UPCOMING
            }

            if (!date.isAfter(today) && date.isAfter(today.minusDays(90))) {
                tags += MovieTag.NOW_PLAYING
            }
        }
    }

    if (voteAverage >= 7 && voteCount >= 500) {
        tags += MovieTag.TOP_RATED
    }

    if (popularity >= 50) {
        tags += MovieTag.POPULAR
    }

    return tags.minByOrNull { it.priority }
}
