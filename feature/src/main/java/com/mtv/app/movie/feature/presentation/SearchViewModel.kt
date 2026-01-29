/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SearchViewModel.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 10.51
 */

package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.feature.event.search.SearchStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    securePrefs: SecurePrefs
) : BaseViewModel(), UiOwner<SearchStateListener, Unit> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(SearchStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(Unit)


}