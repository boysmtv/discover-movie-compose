/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedViewModel.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 12.10
 */

package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.common.StateMovieResult
import com.mtv.app.movie.common.ConstantPreferences.MOVIE_SAVED_LIST
import com.mtv.app.movie.common.DeleteTarget
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.runStateWithActionMovieLocalManager
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.feature.event.liked.LikedDataListener
import com.mtv.app.movie.feature.event.liked.LikedStateListener
import com.mtv.app.movie.feature.utils.MovieLocalManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LikedViewModel @Inject constructor(
    private val movieLocalManager: MovieLocalManager,
) : BaseViewModel(), UiOwner<LikedStateListener, LikedDataListener> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(LikedStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(LikedDataListener())

    init {
        val movieList = movieLocalManager.getAllMovies(MOVIE_SAVED_LIST)
        updateUiDataListener(uiData) { copy(movieLikedList = movieList) }
    }

    fun doDeleteLikedMovies() = uiState.runStateWithActionMovieLocalManager(
        block = {
            movieLocalManager.clearMovies(MOVIE_SAVED_LIST)
        },
        reducer = { state, actionState ->
            state.copy(
                stateMovieResult = actionState,
                deleteSource = DeleteTarget.ALL
            )
        },
        onSuccess = {
            uiData.update {
                it.copy(movieLikedList = emptyList())
            }
        }
    )

    fun doDeleteLikedMoviesById(movieId: Int) = uiState.runStateWithActionMovieLocalManager(
        block = {
            movieLocalManager.removeMovie(MOVIE_SAVED_LIST, movieId)
        },
        reducer = { state, actionState ->
            state.copy(
                stateMovieResult = actionState,
                deleteSource = DeleteTarget.SINGLE
            )
        },
        onSuccess = {
            uiData.update { state ->
                state.copy(
                    movieLikedList = state.movieLikedList.filterNot {
                        it.id == movieId
                    }
                )
            }
        }
    )


    fun onDismissDeleteMovie() {
        uiState.update {
            it.copy(
                stateMovieResult = StateMovieResult.None,
                deleteSource = null
            )
        }
    }

}