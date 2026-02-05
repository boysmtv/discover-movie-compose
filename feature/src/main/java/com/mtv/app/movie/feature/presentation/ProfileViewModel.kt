package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.domain.user.GetProfileUseCase
import com.mtv.app.movie.feature.event.profile.ProfileDataListener
import com.mtv.app.movie.feature.event.profile.ProfileStateListener
import com.mtv.based.core.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val securePrefs: SecurePrefs,
    private val sessionManager: SessionManager,
    private val getProfileUseCase: GetProfileUseCase<LoginResponse>
) : BaseViewModel(),
    UiOwner<ProfileStateListener, ProfileDataListener> {

    override val uiState = MutableStateFlow(ProfileStateListener())
    override val uiData = MutableStateFlow(ProfileDataListener())

    init {
        loadLocalProfile()

        collectFieldSuccessFirebase(
            parent = uiState,
            selector = { it.profileState }
        ) { data ->
            securePrefs.putObject(ConstantPreferences.USER_ACCOUNT, data)
            updateUiDataListener(uiData) { copy(userAccount = data) }
        }
        refreshProfile()
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

    private fun refreshProfile() {
        val uid = sessionManager.getUid() ?: return

        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.profileState },
                set = { copy(profileState = it) }
            ),
            block = {
                getProfileUseCase(uid)
            }
        )
    }

    fun logout() {
        sessionManager.logout()
        uiState.update {
            it.copy(
                logoutState = Resource.Success(Unit)
            )
        }
    }
}
