package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.home.HomeEventListener
import com.mtv.app.movie.feature.event.home.HomeNavigationListener
import com.mtv.app.movie.feature.presentation.HomeViewModel
import com.mtv.app.movie.feature.ui.home.HomeScreen
import com.mtv.app.movie.nav.AppDestinations

@Composable
fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val baseUiState by viewModel.baseUiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val uiData by viewModel.uiData.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        HomeScreen(
            uiState = uiState,
            uiData = uiData,
            uiEvent = HomeEventListener(
                onCheck = viewModel::doCheck,
                onLogout = viewModel::doLogout,
                onLoadMovies = {
                    viewModel.getNowPlaying()
                    viewModel.getPopular()
                    viewModel.getTopRated()
                    viewModel.getUpComing()
                }
            ),
            uiNavigation = HomeNavigationListener(
                onNavigateToLogin = {
                    navController.navigate(AppDestinations.LOGIN_GRAPH) {
                        popUpTo(AppDestinations.LOGIN_GRAPH) { inclusive = true }
                    }
                }
            )
        )
    }

}