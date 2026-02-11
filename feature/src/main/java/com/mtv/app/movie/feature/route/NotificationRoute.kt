/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: NotificationRoute.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 14.09
 */

package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.contract.NotificationDataListener
import com.mtv.app.movie.feature.contract.NotificationEventListener
import com.mtv.app.movie.feature.contract.NotificationNavigationListener
import com.mtv.app.movie.feature.contract.NotificationStateListener
import com.mtv.app.movie.feature.presentation.NotificationViewModel
import com.mtv.app.movie.feature.ui.notif.NotificationScreen

@Composable
fun NotificationRoute(nav: NavController) {
    BaseRoute<NotificationViewModel, NotificationStateListener, NotificationDataListener> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            NotificationScreen(
                uiState = uiState,
                uiData = uiData,
                uiEvent = notificationEvent(vm),
                uiNavigation = notificationNavigation(nav)
            )
        }
    }
}

private fun notificationEvent(vm: NotificationViewModel) = NotificationEventListener(
    onNotificationClicked = {},
    onGetNotification = vm::getLocalNotification,
    onClearNotification = vm::doClearNotification,
    onDismissActiveDialog = vm::doDismissActiveDialog
)

private fun notificationNavigation(nav: NavController) = NotificationNavigationListener(
    onBack = {}
)