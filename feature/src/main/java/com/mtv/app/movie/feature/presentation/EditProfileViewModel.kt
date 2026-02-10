/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: EditProfileViewModel.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.58
 */

package com.mtv.app.movie.feature.presentation

import android.util.Log
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.UpdateProfileRequest
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.domain.user.UpdateProfileUseCase
import com.mtv.app.movie.feature.event.profile.EditProfileDataListener
import com.mtv.app.movie.feature.event.profile.EditProfileDialog
import com.mtv.app.movie.feature.event.profile.EditProfileStateListener
import com.mtv.based.core.network.utils.ResourceFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase<LoginResponse>,
    private val sessionManager: SessionManager,
    private val securePrefs: SecurePrefs
) : BaseViewModel(), UiOwner<EditProfileStateListener, EditProfileDataListener> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(EditProfileStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(EditProfileDataListener())

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

    fun doUpdateProfile(
        name: String,
        phone: String,
        photo: String,
        email: String,
        password: String
    ) {
        val uid = sessionManager.getUid() ?: return

        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.onUpdateState },
                set = { copy(onUpdateState = it) }
            ),
            block = {
                updateProfileUseCase(
                    UpdateProfileRequest(
                        uid = uid,
                        name = name,
                        phone = phone,
                        photo = photo,
                        email = email,
                        password = password
                    )
                )
            },
            onSuccess = { data ->
                uiState.update {
                    it.copy(
                        activeDialog = EditProfileDialog.Success,
                    )
                }
                securePrefs.putObject(ConstantPreferences.USER_ACCOUNT, data)
            }
        )
    }

    fun doDismissActiveDialog() {
        uiState.update {
            it.copy(
                activeDialog = null,
                onUpdateState = ResourceFirebase.Loading,
            )
        }
    }

}

