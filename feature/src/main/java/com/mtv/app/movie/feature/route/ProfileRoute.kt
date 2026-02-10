/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: ProfileRoute.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 15.35
 */

package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.contract.ProfileDataListener
import com.mtv.app.movie.feature.contract.ProfileEventListener
import com.mtv.app.movie.feature.contract.ProfileNavigationListener
import com.mtv.app.movie.feature.contract.ProfileStateListener
import com.mtv.app.movie.feature.presentation.ProfileViewModel
import com.mtv.app.movie.feature.ui.profile.ProfileScreen
import com.mtv.app.movie.nav.AppDestinations

@Composable
fun ProfileRoute(nav: NavController) {
    BaseRoute<ProfileViewModel, ProfileStateListener, ProfileDataListener> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            ProfileScreen(
                uiState = uiState,
                uiData = uiData,
                uiEvent = profileEvent(vm),
                uiNavigation = profileNavigation(nav)
            )
        }
    }
}

private fun profileEvent(vm: ProfileViewModel) = ProfileEventListener(
    onLogout = vm::doLogout,
    onNavigateToSettings = vm::doOpenSetting,
    onDismissActiveDialog = vm::doDismissActiveDialog,
)

private fun profileNavigation(nav: NavController) = ProfileNavigationListener(
    onNavigateToEditProfile = {
        nav.navigate(AppDestinations.EDIT_PROFILE_GRAPH)
    },
    onNavigateToChangePassword = {
        nav.navigate(AppDestinations.CHANGE_PASSWORD_GRAPH)
    },
    navigateToLoginAndClearBackStack = {
        nav.navigate(AppDestinations.LOGIN_GRAPH) {
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
)