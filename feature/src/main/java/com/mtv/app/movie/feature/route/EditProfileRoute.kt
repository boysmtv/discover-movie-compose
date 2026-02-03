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
import com.mtv.app.movie.feature.event.profile.edit.EditProfileEventListener
import com.mtv.app.movie.feature.event.profile.edit.EditProfileNavigationListener
import com.mtv.app.movie.feature.event.profile.edit.EditProfileStateListener
import com.mtv.app.movie.feature.presentation.EditProfileViewModel
import com.mtv.app.movie.feature.ui.profile.edit.EditProfileScreen

@Composable
fun EditProfileRoute(nav: NavController) {
    BaseRoute<EditProfileViewModel, EditProfileStateListener, Unit> { vm, base, uiState, _ ->
        BaseScreen(
            baseUiState = base,
            onDismissError = vm::dismissError
        ) {
            EditProfileScreen(
                uiState = uiState,
                uiEvent = editProfileEvent(vm),
                uiNavigation = editProfileNavigation(nav)
            )
        }
    }
}

private fun editProfileEvent(vm: EditProfileViewModel) = EditProfileEventListener(
    onSaveClicked = vm::doUpdateProfile
)

private fun editProfileNavigation(nav: NavController) = EditProfileNavigationListener(
    onBack = {
        nav.popBackStack()
    }
)

