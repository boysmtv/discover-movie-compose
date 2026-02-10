package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.contract.SplashEventListener
import com.mtv.app.movie.feature.contract.SplashNavigationListener
import com.mtv.app.movie.feature.contract.SplashStateListener
import com.mtv.app.movie.feature.presentation.SplashViewModel
import com.mtv.app.movie.feature.ui.splash.SplashScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopSplash

@Composable
fun SplashRoute(nav: NavController) {
    BaseRoute<SplashViewModel, SplashStateListener, Unit> { vm, base, uiState, _ ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            SplashScreen(
                uiState = uiState,
                uiEvent = splashEvent(vm),
                uiNavigation = splashNavigation(nav)
            )
        }
    }
}

private fun splashEvent(vm: SplashViewModel) = SplashEventListener(
    onDoSplash = vm::doSplash
)

private fun splashNavigation(nav: NavController) = SplashNavigationListener(
    onNavigateToLogin = {
        nav.navigateAndPopSplash(AppDestinations.LOGIN_GRAPH)
    }
)