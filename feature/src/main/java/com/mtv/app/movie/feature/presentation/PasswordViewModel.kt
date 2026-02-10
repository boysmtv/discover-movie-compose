/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: PasswordViewModel.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 16.03
 */

package com.mtv.app.movie.feature.presentation

import android.util.Log
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.movie.common.Constant.Title.CHANGE_PASSWORD_VALIDATE
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.PasswordRequest
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.domain.user.PasswordUseCase
import com.mtv.app.movie.feature.event.profile.PasswordDataListener
import com.mtv.app.movie.feature.event.profile.PasswordDialog
import com.mtv.app.movie.feature.event.profile.PasswordStateListener
import com.mtv.based.core.network.utils.ResourceFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val securePrefs: SecurePrefs,
    private val passwordUseCase: PasswordUseCase
) : BaseViewModel(), UiOwner<PasswordStateListener, PasswordDataListener> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(PasswordStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(PasswordDataListener())

    init {
        loadLocalProfile()
    }

    private fun loadLocalProfile() {
        val localUser = securePrefs.getObject(
            ConstantPreferences.USER_ACCOUNT,
            LoginResponse::class.java
        )

        updateUiDataListener(uiData) {
            copy(userAccount = localUser)
        }
    }

    fun doSubmitPassword(
        password: String,
        newPassword: String,
        newPasswordConfirm: String
    ) {
        if (newPassword != newPasswordConfirm) {
            uiState.update {
                it.copy(activeDialog = PasswordDialog.Validate(CHANGE_PASSWORD_VALIDATE))
            }
            return
        }

        val email = uiData.value.userAccount?.email ?: return

        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.onSubmitPasswordState },
                set = { copy(onSubmitPasswordState = it) }
            ),
            block = {
                passwordUseCase(
                    PasswordRequest(
                        email = email,
                        oldPassword = password,
                        newPassword = newPassword
                    )
                )
            },
            onSuccess = { _ ->
                uiState.update { it.copy(activeDialog = PasswordDialog.Success) }
            }
        )
    }

    fun doDismissActiveDialog() {
        uiState.update {
            it.copy(
                activeDialog = null,
                onSubmitPasswordState = ResourceFirebase.Loading,
            )
        }
    }

}