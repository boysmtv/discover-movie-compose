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
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.domain.movie.MoviesDetailUseCase
import com.mtv.app.movie.domain.movie.MoviesVideosUseCase
import com.mtv.app.movie.feature.event.detail.AddActionState
import com.mtv.app.movie.feature.event.detail.DetailStateListener
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
    fun loadDetailMovies() = launchUseCase(
        target = uiState.valueFlowOf(
            get = { it.detailState },
            set = { state -> copy(detailState = state) }
        ),
        block = { moviesDetailUseCase(movieId) }
    )

    /** GET DETAIL MOVIES */
    fun loadDetailVideos() = launchUseCase(
        target = uiState.valueFlowOf(
            get = { it.videosState },
            set = { state -> copy(videosState = state) }
        ),
        block = { moviesVideosUseCase(movieId) }
    )

    fun onConsumePlayEvent() {
        uiState.update {
            it.copy(videosState = Resource.Loading)
        }
    }

    fun onAddToMyList(movie: MovieDetailResponse) = runCatching {
        movieLocalManager.addMovie(
            ConstantPreferences.MOVIE_SAVED_LIST,
            movie
        )
    }.onSuccess {
        uiState.update {
            it.copy(addMyListState = AddActionState.Success)
        }
    }.onFailure {
        uiState.update {
            it.copy(
                addMyListState = AddActionState.Error(
                    ErrorMessages.GENERIC_ERROR
                )
            )
        }
    }

    fun onAddToMyLike(movie: MovieDetailResponse) = runCatching {
        movieLocalManager.addMovie(
            ConstantPreferences.MOVIE_LIKED_LIST,
            movie
        )
    }.onSuccess {
        uiState.update {
            it.copy(addMyLikeState = AddActionState.Success)
        }
    }.onFailure {
        uiState.update {
            it.copy(
                addMyLikeState = AddActionState.Error(
                    ErrorMessages.GENERIC_ERROR
                )
            )
        }
    }

    fun onShareMovie(movie: MovieDetailResponse) {

    }

    fun onDismissAddMyList() {
        uiState.update { it.copy(addMyListState = AddActionState.None) }
    }

    fun onDismissAddMyLike() {
        uiState.update { it.copy(addMyLikeState = AddActionState.None) }
    }

}