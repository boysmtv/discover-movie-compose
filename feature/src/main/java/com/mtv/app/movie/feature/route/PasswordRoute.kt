/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: PasswordRoute.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 15.35
 */

package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.profile.PasswordDataListener
import com.mtv.app.movie.feature.event.profile.PasswordEventListener
import com.mtv.app.movie.feature.event.profile.PasswordNavigationListener
import com.mtv.app.movie.feature.event.profile.PasswordStateListener
import com.mtv.app.movie.feature.presentation.PasswordViewModel
import com.mtv.app.movie.feature.ui.profile.password.PasswordScreen

@Composable
fun PasswordRoute(nav: NavController) {
    BaseRoute<PasswordViewModel, PasswordStateListener, PasswordDataListener> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            PasswordScreen(
                uiState = uiState,
                uiEvent = passwordEvent(vm),
                uiNavigation = passwordNavigation(nav)
            )
        }
    }
}

private fun passwordEvent(vm: PasswordViewModel) = PasswordEventListener(
    onSubmitPassword = vm::doSubmitPassword,
    onDismissActiveDialog = vm::doDismissActiveDialog
)

private fun passwordNavigation(nav: NavController) = PasswordNavigationListener(
    onBack = {
        nav.popBackStack()
    },
)