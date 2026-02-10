/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: DetailMovieContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 12.48
 */

package com.mtv.app.movie.feature.contract

import com.mtv.app.movie.common.Constant.Title.UNDER_MAINTENANCE
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.data.model.movie.MovieVideoResponse
import com.mtv.based.core.network.utils.Resource

data class DetailStateListener(
    val detailState: Resource<MovieDetailResponse> = Resource.Loading,
    val videosState: Resource<MovieVideoResponse> = Resource.Loading,
    val addMyListState: Resource<Unit> = Resource.Loading,
    val addMyLikeState: Resource<Unit> = Resource.Loading,
    val onShareMovie: Resource<Unit> = Resource.Loading,
    val activeDialog: DetailDialog? = null
)

data class DetailEventListener(
    val onLoadMovies: () -> Unit,
    val onPlayMovies: () -> Unit,
    val onConsumePlayEvent: () -> Unit,
    val onAddToMyList: (movie: MovieDetailResponse) -> Unit,
    val onAddToMyLike: (movie: MovieDetailResponse) -> Unit,
    val onShareMovie: (movie: MovieDetailResponse) -> Unit,
    val onDismissActiveDialog: () -> Unit
)

data class DetailNavigationListener(
    val onNavigateToBack: () -> Unit,
    val onNavigateToPlayMovie: (key: String) -> Unit
)

sealed class DetailDialog {
    data class Maintenance(
        val message: String = UNDER_MAINTENANCE
    ) : DetailDialog()

    data class Error(
        val message: String
    ) : DetailDialog()

    data class AddMyList(
        val message: String
    ) : DetailDialog()
}
