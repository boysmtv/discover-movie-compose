package com.mtv.app.movie.feature.presentation

import androidx.lifecycle.SavedStateHandle
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.domain.movie.MoviesDetailUseCase
import com.mtv.app.movie.feature.event.detail.DetailStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val moviesDetailUseCase: MoviesDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    private val _uiState = MutableStateFlow(DetailStateListener())
    val uiState: StateFlow<DetailStateListener> = _uiState

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    /** GET DETAIL MOVIES */
    fun loadDetailMovies() = launchUseCase(
        target = _uiState.valueFlowOf(
            get = { it.detailState },
            set = { state -> copy(detailState = state) }
        ),
        block = { moviesDetailUseCase(movieId) }
    )
}