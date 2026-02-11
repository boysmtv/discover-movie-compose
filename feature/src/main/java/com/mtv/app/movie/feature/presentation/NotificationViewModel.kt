/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: NotificationViewModel.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 14.15
 */

package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.movie.common.ConstantPreferences.FCM_NOTIFICATION
import com.mtv.app.movie.common.ConstantPreferences.MOVIE_SAVED_LIST
import com.mtv.app.movie.common.DeleteTarget
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.runStateLocalManager
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.response.NotificationData
import com.mtv.app.movie.domain.user.NotificationUseCase
import com.mtv.app.movie.feature.contract.LikedDialog
import com.mtv.app.movie.feature.contract.NotificationDataListener
import com.mtv.app.movie.feature.contract.NotificationDialog
import com.mtv.app.movie.feature.contract.NotificationStateListener
import com.mtv.app.movie.feature.utils.NotificationItem
import com.mtv.based.core.network.utils.ErrorMessages
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationUseCase: NotificationUseCase,
    private val sessionManager: SessionManager,
    private val securePrefs: SecurePrefs,
) : BaseViewModel(), UiOwner<NotificationStateListener, NotificationDataListener> {

    override val uiState = MutableStateFlow(NotificationStateListener())

    override val uiData = MutableStateFlow(NotificationDataListener())

    init {
        getLocalNotification()
    }

    fun getLocalNotification() {
        val localNotification = securePrefs.getObject(
            FCM_NOTIFICATION,
            Array<NotificationItem>::class.java
        )?.toList() ?: emptyList()

        updateUiDataListener(uiData) {
            copy(localNotification = localNotification)
        }
    }

    fun doClearNotification() = uiState.runStateLocalManager(
        block = {
            securePrefs.remove(FCM_NOTIFICATION)
        },
        reducer = { state, actionState ->
            state.copy()
        },
        onSuccess = {
            uiState.update {
                it.copy(
                    activeDialog = NotificationDialog.Success
                )
            }
        },
        onError = { throwable ->
            uiState.update {
                it.copy(
                    activeDialog = NotificationDialog.Error(
                        message = throwable.message
                            ?: ErrorMessages.GENERIC_ERROR
                    )
                )
            }
        }
    )

    fun doDismissActiveDialog() {
        uiState.update {
            it.copy(
                activeDialog = null,
            )
        }
    }

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
