package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.profile.ProfileDataListener
import com.mtv.app.movie.feature.event.profile.ProfileEventListener
import com.mtv.app.movie.feature.event.profile.ProfileNavigationListener
import com.mtv.app.movie.feature.event.profile.ProfileStateListener
import com.mtv.app.movie.feature.presentation.ProfileViewModel
import com.mtv.app.movie.feature.ui.profile.ProfileScreen

@Composable
fun ProfileRoute(navController: NavController) {
    BaseRoute<ProfileViewModel, ProfileStateListener, ProfileDataListener> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            ProfileScreen(
                uiState = uiState,
                uiData = uiData,
                uiEvent = profileEvent(vm),
                uiNavigation = profileNavigation(navController)
            )
        }
    }
}

private fun profileEvent(vm: ProfileViewModel) = ProfileEventListener(
    onEditProfile = { },
    onAddPin = { },
    onInviteFriend = { },
    onLogout = { },
)

private fun profileNavigation(nav: NavController) = ProfileNavigationListener(
    onNavigateToSettings = { }
)