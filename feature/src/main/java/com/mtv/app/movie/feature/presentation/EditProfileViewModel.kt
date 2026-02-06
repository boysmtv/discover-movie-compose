/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: EditProfileViewModel.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.58
 */

package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.ConstantPreferences.PROFILE_PHOTO_STRING
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.UpdateProfileRequest
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.domain.user.UpdateProfileUseCase
import com.mtv.app.movie.feature.event.profile.edit.EditProfileDataListener
import com.mtv.app.movie.feature.event.profile.edit.EditProfileStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
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
        photo: String?,
        password: String?
    ) {
        val uid = sessionManager.getUid() ?: return

        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.updateState },
                set = { copy(updateState = it) }
            ),
            block = {
                updateProfileUseCase(
                    UpdateProfileRequest(
                        uid = uid,
                        name = name,
                        phone = phone,
                        photo = photo
                    )
                )
            }
        )
    }
}

