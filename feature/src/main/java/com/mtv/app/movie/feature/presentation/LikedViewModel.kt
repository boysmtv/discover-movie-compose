/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedViewModel.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 12.10
 */

package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.movie.common.ConstantPreferences.MOVIE_SAVED_LIST
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.feature.event.liked.LikedDataListener
import com.mtv.app.movie.feature.event.liked.LikedStateListener
import com.mtv.app.movie.feature.utils.MovieLocalManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LikedViewModel @Inject constructor(
    movieLocalManager: MovieLocalManager,
) : BaseViewModel(), UiOwner<LikedStateListener, LikedDataListener> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(LikedStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(LikedDataListener())

    init {
        val movieList = movieLocalManager.getMovies(MOVIE_SAVED_LIST)
        updateUiDataListener(uiData) { copy(movieLikedList = movieList) }
    }

}