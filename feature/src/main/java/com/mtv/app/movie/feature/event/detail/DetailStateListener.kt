/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: DetailStateListener.kt
 *
 * Last modified by Dedy Wijaya on 02/02/26 09.43
 */

package com.mtv.app.movie.feature.event.detail

import com.mtv.app.movie.common.StateMovieResult
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.data.model.movie.MovieVideoResponse
import com.mtv.based.core.network.utils.Resource

data class DetailStateListener(
    val detailState: Resource<MovieDetailResponse> = Resource.Loading,
    val videosState: Resource<MovieVideoResponse> = Resource.Loading,
    val addMyListState: StateMovieResult = StateMovieResult.None,
    val addMyLikeState: StateMovieResult = StateMovieResult.None
)