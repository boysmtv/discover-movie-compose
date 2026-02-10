/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedRoute.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 12.10
 */

package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.contract.LikedDataListener
import com.mtv.app.movie.feature.contract.LikedEventListener
import com.mtv.app.movie.feature.contract.LikedNavigationListener
import com.mtv.app.movie.feature.contract.LikedStateListener
import com.mtv.app.movie.feature.presentation.LikedViewModel
import com.mtv.app.movie.feature.ui.liked.LikedScreen
import com.mtv.app.movie.nav.AppDestinations

@Composable
fun LikedRoute(nav: NavController) {
    BaseRoute<LikedViewModel, LikedStateListener, LikedDataListener> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            LikedScreen(
                uiState = uiState,
                uiData = uiData,
                uiEvent = likedEvent(vm),
                uiNavigation = likedNavigation(nav)
            )
        }
    }
}

private fun likedEvent(vm: LikedViewModel) = LikedEventListener(
    onLoadLikedMovies = { },
    onDeletedMovie = vm::doDeleteLikedMoviesById,
    onDeletedAllMovies = vm::doDeleteLikedMovies,
    onDismissActiveDialog = vm::doDismissActiveDialog
)

private fun likedNavigation(nav: NavController) = LikedNavigationListener(
    onNavigateToMovieDetail = { movieId ->
        nav.navigate(AppDestinations.navigateToDetailMovies(movieId))
    }
)