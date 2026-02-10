/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 13.15
 */

package com.mtv.app.movie.feature.contract

import com.mtv.app.movie.common.DeleteTarget
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.ERROR_STRING

data class LikedStateListener(
    val onDeleteState: Resource<Unit> = Resource.Loading,
    val deleteSource: DeleteTarget? = null,
    val activeDialog: LikedDialog? = null
)

data class LikedDataListener(
    val movieLikedList: List<MovieDetailResponse> = emptyList(),
)

data class LikedEventListener(
    val onLoadLikedMovies: (movie: MovieDetailResponse) -> Unit,
    val onDeletedMovie: (movieId: Int) -> Unit,
    val onDeletedAllMovies: () -> Unit,
    val onDismissActiveDialog: () -> Unit
)

data class LikedNavigationListener(
    val onNavigateToMovieDetail: (movieId: Int) -> Unit,
)

sealed class LikedDialog {
    data class Error(
        val message: String = ERROR_STRING
    ) : LikedDialog()

    object Success : LikedDialog()
}