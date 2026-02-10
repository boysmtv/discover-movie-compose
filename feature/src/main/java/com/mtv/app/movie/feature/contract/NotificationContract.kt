/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: NotificationContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 14.13
 */

package com.mtv.app.movie.feature.contract

import com.mtv.app.movie.common.Constant.Title.UNDER_MAINTENANCE
import com.mtv.based.core.network.utils.ResourceFirebase

data class NotificationStateListener(
    val notificationState: ResourceFirebase<String> = ResourceFirebase.Loading,
    val activeDialog: NotificationDialog? = null
)

data class NotificationEventListener(
    val onNotificationClicked: () -> Unit,
    val onDismissActiveDialog: () -> Unit,
)

data class NotificationNavigationListener(
    val onBack: () -> Unit,
)

sealed class NotificationDialog {
    data class Maintenance(
        val message: String = UNDER_MAINTENANCE
    ) : NotificationDialog()
}