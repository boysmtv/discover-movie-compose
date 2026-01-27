package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.reset.ResetEventListener
import com.mtv.app.movie.feature.event.reset.ResetNavigationListener
import com.mtv.app.movie.feature.presentation.ResetViewModel
import com.mtv.app.movie.feature.ui.reset.ResetScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopRegister

@Composable
fun ResetRoute(
    navController: NavController,
    viewModel: ResetViewModel = hiltViewModel(),
) {
    val baseUiState by viewModel.baseUiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        ResetScreen(
            uiState = uiState,
            uiEvent = ResetEventListener(
                onResetPasswordClicked = viewModel::resetPassword,
            ),
            uiNavigation = ResetNavigationListener(
                onNavigateToLogin = {
                    navController.navigateAndPopRegister(AppDestinations.LOGIN_GRAPH)
                },
                onRegisterByGoogle = {},
                onRegisterByFacebook = {},
            )
        )
    }
}