package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.register.RegisterEventListener
import com.mtv.app.movie.feature.event.register.RegisterNavigationListener
import com.mtv.app.movie.feature.event.register.RegisterStateListener
import com.mtv.app.movie.feature.event.splash.SplashEventListener
import com.mtv.app.movie.feature.event.splash.SplashNavigationListener
import com.mtv.app.movie.feature.event.splash.SplashStateListener
import com.mtv.app.movie.feature.presentation.RegisterViewModel
import com.mtv.app.movie.feature.presentation.SplashViewModel
import com.mtv.app.movie.feature.ui.register.RegisterScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopRegister
import com.mtv.app.movie.nav.navigateAndPopSplash

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
    onRegisterClicked = vm::doRegister,
)

private fun registerNavigation(nav: NavController) = RegisterNavigationListener(
    onNavigateToLogin = {
        nav.navigateAndPopRegister(AppDestinations.LOGIN_GRAPH)
    },
    onRegisterByGoogle = {},
    onRegisterByFacebook = {},
)