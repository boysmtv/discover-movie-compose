package com.mtv.app.movie.feature.presentation

import androidx.lifecycle.SavedStateHandle
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.domain.movie.MoviesDetailUseCase
import com.mtv.app.movie.domain.movie.MoviesVideosUseCase
import com.mtv.app.movie.feature.event.detail.DetailStateListener
import com.mtv.based.core.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesDetailUseCase: MoviesDetailUseCase,
    private val moviesVideosUseCase: MoviesVideosUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    private val _uiState = MutableStateFlow(DetailStateListener())
    val uiState: StateFlow<DetailStateListener> = _uiState

    /** MOVIE ID FROM STATE */
    private val movieId: Int = checkNotNull(savedStateHandle[Constant.SharedParam.MOVIE_ID])

    /** GET DETAIL MOVIES */
    fun loadDetailMovies() = launchUseCase(
        target = _uiState.valueFlowOf(
            get = { it.detailState },
            set = { state -> copy(detailState = state) }
        ),
        block = { moviesDetailUseCase(movieId) }
    )

    /** GET DETAIL MOVIES */
    fun loadDetailVideos() = launchUseCase(
        target = _uiState.valueFlowOf(
            get = { it.videosState },
            set = { state -> copy(videosState = state) }
        ),
        block = { moviesVideosUseCase(movieId) }
    )

    fun onConsumePlayEvent() {
        _uiState.update {
            it.copy(videosState = Resource.Loading)
        }
    }

}