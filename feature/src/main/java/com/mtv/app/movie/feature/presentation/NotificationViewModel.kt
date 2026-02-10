/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: NotificationViewModel.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 14.15
 */

package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.response.NotificationData
import com.mtv.app.movie.domain.user.NotificationUseCase
import com.mtv.app.movie.feature.contract.NotificationStateListener
import com.mtv.app.movie.feature.utils.NotificationItem
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationUseCase: NotificationUseCase,
    private val sessionManager: SessionManager,
) : BaseViewModel(), UiOwner<NotificationStateListener, Unit> {

    override val uiState = MutableStateFlow(NotificationStateListener())

    override val uiData = MutableStateFlow(Unit)


    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    fun sendNotification(title: String, message: String) {

        val uid = sessionManager.getUid() ?: EMPTY_STRING

        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.notificationState },
                set = { state -> copy(notificationState = state) }
            ),
            block = {
                notificationUseCase(
                    NotificationData(
                        title = title,
                        message = message,
                        date = Date(),
                        uid = uid,
                        isRead = false
                    )
                )
            },
            onSuccess = {
                // bisa update UI atau reset input
            }
        )
    }
}
