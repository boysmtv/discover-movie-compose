package com.mtv.app.movie.feature.route.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.detail.DetailEventListener
import com.mtv.app.movie.feature.event.detail.DetailNavigationListener
import com.mtv.app.movie.feature.presentation.MoviesDetailViewModel
import com.mtv.app.movie.feature.ui.detail.DetailMovieScreen

@Composable
fun MovieDetailRoute(
    navController: NavController,
    viewModel: MoviesDetailViewModel = hiltViewModel(),
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
                onPlayMovies = {}
            ),
            uiNavigation = DetailNavigationListener(
                onNavigateToBack = {
                    navController.popBackStack()
                },
                onNavigateToPlayMovie = {}
            )
        )

    }
}