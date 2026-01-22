package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.login.LoginEventListener
import com.mtv.app.movie.feature.event.login.LoginNavigationListener
import com.mtv.app.movie.feature.presentation.LoginViewModel
import com.mtv.app.movie.feature.ui.login.LoginScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopLogin

@Composable
fun LoginRoute(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val baseUiState by viewModel.baseUiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        LoginScreen(
            uiState = uiState,
            uiEvent = LoginEventListener(
                onLoginClicked = viewModel::doLogin
            ),
            uiNavigation = LoginNavigationListener(
                onNavigateToHome = {
                    navController.navigateAndPopLogin(AppDestinations.HOME_GRAPH)
                },
                onNavigateToSignUpByEmail = {
                    navController.navigateAndPopLogin(AppDestinations.REGISTER_GRAPH)
                },
                onNavigateToSignUpByGoogle = {},
                onNavigateToSignUpByFacebook = {},
                onNavigateToForgotPassword = {
                    navController.navigateAndPopLogin(AppDestinations.FORGOT_PASSWORD_GRAPH)
                }
            )
        )
    }
}