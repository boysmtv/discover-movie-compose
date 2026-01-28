package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.feature.event.home.HomeDataListener
import com.mtv.app.movie.feature.event.home.HomeStateListener
import com.mtv.app.movie.feature.event.profile.ProfileDataListener
import com.mtv.app.movie.feature.event.profile.ProfileStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    securePrefs: SecurePrefs
) : BaseViewModel(), UiOwner<ProfileStateListener, ProfileDataListener> {

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiState = MutableStateFlow(ProfileStateListener())
    override val uiData = MutableStateFlow(ProfileDataListener())

    init {
        val userAccount = securePrefs.getObject(
            key = ConstantPreferences.USER_ACCOUNT,
            clazz = LoginResponse::class.java
        )
        updateUiDataListener(uiData) { copy(userAccount = userAccount) }
    }

}