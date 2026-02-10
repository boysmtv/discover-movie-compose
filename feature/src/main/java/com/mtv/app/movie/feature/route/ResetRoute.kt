package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.contract.ResetEventListener
import com.mtv.app.movie.feature.contract.ResetNavigationListener
import com.mtv.app.movie.feature.contract.ResetStateListener
import com.mtv.app.movie.feature.presentation.ResetViewModel
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