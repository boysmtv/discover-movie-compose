/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: EditProfileRoute.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 12.08
 */

package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.profile.EditProfileDataListener
import com.mtv.app.movie.feature.event.profile.EditProfileEventListener
import com.mtv.app.movie.feature.event.profile.EditProfileNavigationListener
import com.mtv.app.movie.feature.event.profile.EditProfileStateListener
import com.mtv.app.movie.feature.presentation.EditProfileViewModel
import com.mtv.app.movie.feature.ui.profile.edit.EditProfileScreen

@Composable
fun EditProfileRoute(nav: NavController) {
    BaseRoute<EditProfileViewModel, EditProfileStateListener, EditProfileDataListener> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            EditProfileScreen(
                uiState = uiState,
                uiData = uiData,
                uiEvent = editProfileEvent(vm),
                uiNavigation = editProfileNavigation(nav)
            )
        }
    }
}

private fun editProfileEvent(vm: EditProfileViewModel) = EditProfileEventListener(
    onSaveClicked = vm::doUpdateProfile,
)

private fun editProfileNavigation(nav: NavController) = EditProfileNavigationListener(
    onBack = {
        nav.popBackStack()
    }
)