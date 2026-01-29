package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.login.LoginEventListener
import com.mtv.app.movie.feature.event.login.LoginNavigationListener
import com.mtv.app.movie.feature.event.login.LoginStateListener
import com.mtv.app.movie.feature.presentation.LoginViewModel
import com.mtv.app.movie.feature.ui.login.LoginScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopLogin

@Composable
fun LoginRoute(nav: NavController) {
    BaseRoute<LoginViewModel, LoginStateListener, Unit> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            LoginScreen(
                uiState = uiState,
                uiEvent = loginEvent(vm),
                uiNavigation = loginNavigation(nav)
            )
        }
    }
}

private fun loginEvent(vm: LoginViewModel) = LoginEventListener(
    onLoginClicked = vm::doLogin
)

private fun loginNavigation(nav: NavController) = LoginNavigationListener(
    onNavigateToHome = {
        nav.navigateAndPopLogin(
            route = AppDestinations.HOME_GRAPH
        )
    },
    onNavigateToSignUpByEmail = {
        nav.navigateAndPopLogin(
            route = AppDestinations.REGISTER_GRAPH
        )
    },
    onNavigateToForgotPassword = {
        nav.navigateAndPopLogin(
            route = AppDestinations.RESET_GRAPH
        )
    },
    onNavigateToSignUpByGoogle = {},
    onNavigateToSignUpByFacebook = {},
)