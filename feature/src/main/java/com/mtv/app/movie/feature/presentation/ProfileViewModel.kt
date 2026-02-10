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
import com.mtv.app.movie.feature.event.profile.ProfileDialog
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

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(ProfileStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(ProfileDataListener())

    init {
        loadLocalProfile()
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
                get = { it.onProfileState },
                set = { copy(onProfileState = it) }
            ),
            block = {
                getProfileUseCase(uid)
            },
            onSuccess = { data ->
                securePrefs.putObject(ConstantPreferences.USER_ACCOUNT, data)
                updateUiDataListener(uiData) { copy(userAccount = data) }
            }
        )
    }

    fun doOpenSetting() {
        uiState.update {
            it.copy(
                onSettingState = Resource.Success(Unit),
                activeDialog = ProfileDialog.Maintenance(
                    message = "Under maintenance"
                )
            )
        }
    }

    fun doDismissActiveDialog() {
        uiState.update {
            it.copy(
                activeDialog = null,
                onAddPinState = Resource.Loading,
                onSettingState = Resource.Loading
            )
        }
    }

    fun doLogout() {
        sessionManager.logout()
        uiState.update {
            it.copy(
                onLogoutState = Resource.Success(Unit)
            )
        }
    }
}
