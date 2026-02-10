/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: MovieDetailViewModel.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 10.50
 */

package com.mtv.app.movie.feature.presentation

import androidx.lifecycle.SavedStateHandle
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.common.ConstantPreferences.MOVIE_SAVED_LIST
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.runStateMovieLocalManager
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.domain.movie.MoviesDetailUseCase
import com.mtv.app.movie.domain.movie.MoviesVideosUseCase
import com.mtv.app.movie.feature.contract.DetailDialog
import com.mtv.app.movie.feature.contract.DetailStateListener
import com.mtv.app.movie.feature.utils.MovieLocalManager
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesDetailUseCase: MoviesDetailUseCase,
    private val moviesVideosUseCase: MoviesVideosUseCase,
    private val movieLocalManager: MovieLocalManager,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(), UiOwner<DetailStateListener, Unit> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(DetailStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(Unit)

    /** MOVIE ID FROM STATE */
    private val movieId: Int = checkNotNull(savedStateHandle[Constant.SharedParam.MOVIE_ID])

    /** GET DETAIL MOVIES */
    fun doLoadDetailMovies() = launchUseCase(
        loading = false,
        target = uiState.valueFlowOf(
            get = { it.detailState },
            set = { state -> copy(detailState = state) }
        ),
        block = { moviesDetailUseCase(movieId) }
    )

    /** GET DETAIL MOVIES */
    fun doLoadDetailVideos() = launchUseCase(
        target = uiState.valueFlowOf(
            get = { it.videosState },
            set = { state -> copy(videosState = state) }
        ),
        block = { moviesVideosUseCase(movieId) }
    )

    fun doConsumePlayEvent() {
        uiState.update {
            it.copy(videosState = Resource.Loading)
        }
    }

    fun doAddToMyList(movie: MovieDetailResponse) = uiState.runStateMovieLocalManager(
        block = {
            movieLocalManager.addMovie(MOVIE_SAVED_LIST, movie)
        },
        reducer = { state, actionState ->
            state.copy(addMyListState = actionState)
        },
        onSuccess = {
            uiState.update {
                it.copy(
                    activeDialog = DetailDialog.AddMyList("Movie added to your list")
                )
            }
        },
        onError = { throwable ->
            uiState.update {
                it.copy(
                    activeDialog = DetailDialog.Error(
                        message = throwable.message
                            ?: ErrorMessages.GENERIC_ERROR
                    )
                )
            }
        }
    )

    fun doAddToMyLike(movie: MovieDetailResponse) = uiState.update {
        it.copy(
            addMyLikeState = Resource.Success(Unit),
            activeDialog = DetailDialog.Maintenance()
        )
    }

    fun doShareMovie(movie: MovieDetailResponse) = uiState.update {
        it.copy(
            onShareMovie = Resource.Success(Unit),
            activeDialog = DetailDialog.Maintenance()
        )
    }

    fun doDismissActiveDialog() = uiState.update {
        it.copy(
            activeDialog = null,
            addMyListState = Resource.Loading,
            addMyLikeState = Resource.Loading,
            onShareMovie = Resource.Loading,
        )
    }

}