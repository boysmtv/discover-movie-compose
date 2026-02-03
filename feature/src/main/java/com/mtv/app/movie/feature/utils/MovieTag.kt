/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: MovieTag.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.02
 */

package com.mtv.app.movie.feature.utils

import androidx.compose.ui.graphics.Color

enum class MovieTag(
    val label: String,
    val color: Color,
    val priority: Int
) {
    UPCOMING(label = "Upcoming", color = Color(0xFF1E88E5), priority = 1),
    NOW_PLAYING(label = "Now Playing", color = Color(0xFF43A047), priority = 2),
    TOP_RATED(label = "Top Rated", color = Color(0xFFFFC107), priority = 3),
    POPULAR(label = "Popular", color = Color(0xFFE53935), priority = 4)
}
