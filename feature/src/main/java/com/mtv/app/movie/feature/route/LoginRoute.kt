package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.contract.LoginEventListener
import com.mtv.app.movie.feature.contract.LoginNavigationListener
import com.mtv.app.movie.feature.contract.LoginStateListener
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
    onLoginByEmailClicked = vm::doLoginByEmail,
    onLoginByGoogleClicked = vm::doLoginByGoogle,
    onLoginByFacebookClicked = vm::doLoginByFacebook,
    onDismissActiveDialog = vm::doDismissActiveDialog,
)

private fun loginNavigation(nav: NavController) = LoginNavigationListener(
    onNavigateToHome = {
        nav.navigateAndPopLogin(
            route = AppDestinations.HOME_GRAPH
        )
    },
    onNavigateToSignUpByEmail = {
        nav.navigate(AppDestinations.REGISTER_GRAPH)
    },
    onNavigateToForgotPassword = {
        nav.navigate(AppDestinations.RESET_GRAPH)
    }
)