package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.register.RegisterEventListener
import com.mtv.app.movie.feature.event.register.RegisterNavigationListener
import com.mtv.app.movie.feature.presentation.RegisterViewModel
import com.mtv.app.movie.feature.ui.register.RegisterScreen
import com.mtv.app.movie.nav.AppDestinations

@Composable
fun RegisterRoute(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val baseUiState by viewModel.baseUiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        RegisterScreen(
            uiState = uiState,
            uiEvent = RegisterEventListener(
                onRegisterClicked = viewModel::doRegister,
            ),
            uiNavigation = RegisterNavigationListener(
                onNavigateToLogin = {
                    navController.navigate(AppDestinations.LOGIN_GRAPH) {
                        popUpTo(AppDestinations.REGISTER_GRAPH) { inclusive = true }
                    }
                },
                onRegisterByGoogle = {},
                onRegisterByFacebook = {},
            )

        )
    }
}