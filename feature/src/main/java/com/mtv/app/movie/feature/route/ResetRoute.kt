package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.reset.ResetEventListener
import com.mtv.app.movie.feature.event.reset.ResetNavigationListener
import com.mtv.app.movie.feature.event.reset.ResetStateListener
import com.mtv.app.movie.feature.event.splash.SplashStateListener
import com.mtv.app.movie.feature.presentation.ResetViewModel
import com.mtv.app.movie.feature.presentation.SplashViewModel
import com.mtv.app.movie.feature.ui.reset.ResetScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopRegister

@Composable
fun ResetRoute(nav: NavController) {
    BaseRoute<ResetViewModel, ResetStateListener, Unit> { vm, base, uiState, _ ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            ResetScreen(
                uiState = uiState,
                uiEvent = splashEvent(vm),
                uiNavigation = splashNavigation(nav)
            )
        }
    }
}

private fun splashEvent(vm: ResetViewModel) = ResetEventListener(
    onResetPasswordClicked = vm::resetPassword,
)

private fun splashNavigation(nav: NavController) = ResetNavigationListener(
    onNavigateToLogin = {
        nav.navigateAndPopRegister(AppDestinations.LOGIN_GRAPH)
    },
    onRegisterByGoogle = {},
    onRegisterByFacebook = {},
)