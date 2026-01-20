package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.presentation.HomeViewModel
import com.mtv.app.movie.feature.ui.home.HomeScreen
import com.mtv.app.movie.nav.AppDestinations

@Composable
fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val baseUiState by viewModel.baseUiState.collectAsState()

    val checkState by viewModel.checkState.collectAsState()
    val logoutState by viewModel.logoutState.collectAsState()

    val nowPlayingState by viewModel.nowPlayingState.collectAsState()
    val popularState by viewModel.popularState.collectAsState()
    val topRatedState by viewModel.topRatedState.collectAsState()
    val upComingState by viewModel.upComingState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        HomeScreen(
            checkState = checkState,
            logoutState = logoutState,
            nowPlayingState = nowPlayingState,
            popularState = popularState,
            topRatedState = topRatedState,
            upComingState = upComingState,
            onDoCheck = { email -> viewModel.doCheck(email) },
            onDoLogout = { email -> viewModel.doLogout(email) },
            onDoGetNowPlaying = { viewModel.getNowPlayingUseCase() },
            onSuccessLogout = {
                navController.navigate(AppDestinations.LOGIN_GRAPH) {
                    popUpTo(AppDestinations.LOGIN_GRAPH) { inclusive = true }
                }
            }
        )
    }

}