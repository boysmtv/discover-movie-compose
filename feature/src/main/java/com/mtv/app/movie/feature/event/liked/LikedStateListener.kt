/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedStateListener.kt
 *
 * Last modified by Dedy Wijaya on 02/02/26 09.43
 */

package com.mtv.app.movie.feature.event.liked

import com.mtv.app.movie.common.DeleteTarget
import com.mtv.app.movie.common.StateMovieResult

data class LikedStateListener(
    val stateMovieResult: StateMovieResult = StateMovieResult.None,
    val deleteSource: DeleteTarget? = null
)
