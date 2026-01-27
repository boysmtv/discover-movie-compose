package com.mtv.app.movie.feature.route.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.detail.DetailEventListener
import com.mtv.app.movie.feature.event.detail.DetailNavigationListener
import com.mtv.app.movie.feature.presentation.MovieDetailViewModel
import com.mtv.app.movie.feature.ui.detail.DetailMovieScreen
import com.mtv.app.movie.nav.AppDestinations

@Composable
fun MovieDetailRoute(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    val baseUiState by viewModel.baseUiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        DetailMovieScreen(
            uiState = uiState,
            uiEvent = DetailEventListener(
                onLoadMovies = viewModel::loadDetailMovies,
                onPlayMovies = viewModel::loadDetailVideos,
                onConsumePlayEvent = viewModel::onConsumePlayEvent,
                onAddToMyList = viewModel::onAddToMyList,
                onAddToMyLike = viewModel::onAddToMyLike,
                onShareMovie = viewModel::onShareMovie,
                onDismissAddMyList = viewModel::onDismissAddMyList,
                onDismissAddMyLike = viewModel::onDismissAddMyLike,
            ),
            uiNavigation = DetailNavigationListener(
                onNavigateToBack = {
                    navController.popBackStack()
                },
                onNavigateToPlayMovie = { key ->
                    navController.navigate(AppDestinations.navigateToPlayMovie(key))
                }
            )
        )
    }
}