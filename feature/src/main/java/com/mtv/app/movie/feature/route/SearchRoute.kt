/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SearchRoute.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 10.50
 */

package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.search.SearchEventListener
import com.mtv.app.movie.feature.event.search.SearchNavigationListener
import com.mtv.app.movie.feature.event.search.SearchStateListener
import com.mtv.app.movie.feature.presentation.SearchViewModel
import com.mtv.app.movie.feature.ui.search.SearchScreen

@Composable
fun SearchRoute(nav: NavController) {
    BaseRoute<SearchViewModel, SearchStateListener, Unit> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            SearchScreen(
                uiState = uiState,
                uiEvent = searchEvent(vm),
                uiNavigation = searchNavigation(nav)
            )
        }
    }
}

private fun searchEvent(vm: SearchViewModel) = SearchEventListener(
    onDoSplash = { }
)


private fun searchNavigation(nav: NavController) = SearchNavigationListener(
    emptyNavigate = { }
)