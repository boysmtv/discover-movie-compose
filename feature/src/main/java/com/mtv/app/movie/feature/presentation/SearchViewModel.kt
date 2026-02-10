/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SearchViewModel.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 10.51
 */

package com.mtv.app.movie.feature.presentation

import androidx.lifecycle.viewModelScope
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.domain.movie.MoviesUpComingUseCase
import com.mtv.app.movie.domain.movie.SearchMovieUseCase
import com.mtv.app.movie.feature.contract.SearchDataListener
import com.mtv.app.movie.feature.contract.SearchStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val upComingUseCase: MoviesUpComingUseCase
) : BaseViewModel(), UiOwner<SearchStateListener, SearchDataListener> {

    override val uiState = MutableStateFlow(SearchStateListener())
    override val uiData = MutableStateFlow(SearchDataListener())

    init {
        collectQuery()
    }

    fun collectQuery() {
        viewModelScope.launch {
            uiData.map { it.query }
                .debounce(500)
                .distinctUntilChanged()
                .collect { query -> if (query.isNotBlank()) performSearch(query) else fetchUpComingMovies() }
        }
    }

    fun onSearch(query: String) {
        uiData.update { it.copy(query = query) }
    }

    private fun performSearch(query: String) {
        launchUseCase(
            loading = false,
            target = uiState.valueFlowOf(
                get = { it.searchState },
                set = { state -> copy(searchState = state) }
            ),
            block = {
                searchMovieUseCase(query)
            },
            onSuccess = { data ->
                uiData.update { it.copy(query = it.query, movies = data.results) }
            }
        )
    }

    private fun fetchUpComingMovies() {
        launchUseCase(
            loading = false,
            target = uiState.valueFlowOf(
                get = { it.upComingState },
                set = { state -> copy(upComingState = state) }
            ),
            block = {
                upComingUseCase(Unit)
            },
            onSuccess = { data ->
                if (uiData.value.query.isBlank()) uiData.update { it.copy(query = it.query, movies = data.results) }
            }
        )
    }

}