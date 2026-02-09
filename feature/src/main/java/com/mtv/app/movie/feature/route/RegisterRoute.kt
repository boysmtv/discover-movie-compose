package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.register.RegisterEventListener
import com.mtv.app.movie.feature.event.register.RegisterNavigationListener
import com.mtv.app.movie.feature.event.register.RegisterStateListener
import com.mtv.app.movie.feature.presentation.RegisterViewModel
import com.mtv.app.movie.feature.ui.register.RegisterScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopRegister

@Composable
fun RegisterRoute(nav: NavController) {
    BaseRoute<RegisterViewModel, RegisterStateListener, Unit> { vm, base, uiState, _ ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            RegisterScreen(
                uiState = uiState,
                uiEvent = registerEvent(vm),
                uiNavigation = registerNavigation(nav)
            )
        }
    }
}

private fun registerEvent(vm: RegisterViewModel) = RegisterEventListener(
    onRegisterByEmailClicked = vm::doRegisterByEmail,
    onRegisterByGoogleClicked = vm::doLoginByGoogle,
    onRegisterByFacebookClicked = vm::doLoginByFacebook,
    onDismissActiveDialog = vm::doDismissActiveDialog,
)

private fun registerNavigation(nav: NavController) = RegisterNavigationListener(
    onNavigateToLogin = {
        nav.navigateAndPopRegister(AppDestinations.LOGIN_GRAPH)
    },
)