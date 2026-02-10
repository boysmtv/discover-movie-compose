package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.contract.DetailEventListener
import com.mtv.app.movie.feature.contract.DetailNavigationListener
import com.mtv.app.movie.feature.contract.DetailStateListener
import com.mtv.app.movie.feature.presentation.MovieDetailViewModel
import com.mtv.app.movie.feature.ui.detail.DetailMovieScreen
import com.mtv.app.movie.nav.AppDestinations

@Composable
fun MovieDetailRoute(nav: NavController) {
    BaseRoute<MovieDetailViewModel, DetailStateListener, Unit> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            DetailMovieScreen(
                uiState = uiState,
                uiEvent = detailEvent(vm),
                uiNavigation = detailNavigation(nav)
            )
        }
    }
}

private fun detailEvent(vm: MovieDetailViewModel) = DetailEventListener(
    onLoadMovies = vm::doLoadDetailMovies,
    onPlayMovies = vm::doLoadDetailVideos,
    onConsumePlayEvent = vm::doConsumePlayEvent,
    onAddToMyList = vm::doAddToMyList,
    onAddToMyLike = vm::doAddToMyLike,
    onShareMovie = vm::doShareMovie,
    onDismissActiveDialog = vm::doDismissActiveDialog,
)

private fun detailNavigation(nav: NavController) = DetailNavigationListener(
    onNavigateToBack = {
        nav.popBackStack()
    },
    onNavigateToPlayMovie = { key ->
        nav.navigate(AppDestinations.navigateToPlayMovie(key))
    }
)