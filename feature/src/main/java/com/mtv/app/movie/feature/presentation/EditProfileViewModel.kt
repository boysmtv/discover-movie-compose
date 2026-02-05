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
import com.mtv.app.movie.common.ConstantPreferences.PROFILE_PHOTO_STRING
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.UpdateProfileRequest
import com.mtv.app.movie.domain.user.UpdateProfileUseCase
import com.mtv.app.movie.feature.event.profile.edit.EditProfileStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val sessionManager: SessionManager,
    private val securePrefs: SecurePrefs
) : BaseViewModel(), UiOwner<EditProfileStateListener, Unit> {

    override val uiState = MutableStateFlow(EditProfileStateListener())
    override val uiData = MutableStateFlow(Unit)

    fun onPhotoSelected(base62Image: String) {
        securePrefs.putString(PROFILE_PHOTO_STRING, base62Image)
    }

    fun doUpdateProfile(
        name: String,
        phone: String,
        photoBaseString: String?
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
                        photoUrl = photoBaseString
                    )
                )
            }
        )
    }
}

