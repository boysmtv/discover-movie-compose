/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: NotificationContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 14.13
 */

package com.mtv.app.movie.feature.contract

import android.os.Message
import com.mtv.app.movie.feature.utils.NotificationItem
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.ERROR_STRING

data class NotificationStateListener(
    val notificationState: ResourceFirebase<String> = ResourceFirebase.Loading,
    val activeDialog: NotificationDialog? = null
)

data class NotificationDataListener(
    val localNotification: List<NotificationItem> = emptyList(),
)

data class NotificationEventListener(
    val onNotificationClicked: (item: NotificationItem) -> Unit,
    val onClearNotification: () -> Unit,
    val onDismissActiveDialog: () -> Unit,
)

data class NotificationNavigationListener(
    val onBack: () -> Unit,
)

sealed class NotificationDialog {
    data class Error(
        val message: String = ERROR_STRING
    ) : NotificationDialog()

    object Success : NotificationDialog()
}