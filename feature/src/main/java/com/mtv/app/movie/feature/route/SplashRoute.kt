package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.splash.SplashEventListener
import com.mtv.app.movie.feature.event.splash.SplashNavigationListener
import com.mtv.app.movie.feature.presentation.SplashViewModel
import com.mtv.app.movie.feature.ui.splash.SplashScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopSplash

@Composable
fun SplashRoute(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val baseUiState by viewModel.baseUiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        SplashScreen(
            uiState = uiState,
            uiEvent = SplashEventListener(
                onDoSplash = viewModel::doSplash
            ),
            uiNavigation = SplashNavigationListener(
                onNavigateToLogin = {
                    navController.navigateAndPopSplash(AppDestinations.LOGIN_GRAPH)
                }
            ),
        )
    }

}