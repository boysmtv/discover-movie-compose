package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.core.provider.utils.device.InstallationIdProvider
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.domain.user.LoginUseCase
import com.mtv.app.movie.feature.event.login.LoginStateListener
import com.mtv.based.core.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase<LoginResponse>,
    private val installationIdProvider: InstallationIdProvider,
    private val securePrefs: SecurePrefs
) : BaseViewModel(), UiOwner<LoginStateListener, Unit> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(LoginStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(Unit)

    /** INIT */
    init {
        collectFieldSuccessFirebase(
            parent = uiState,
            selector = { it.loginByEmailState }
        ) { data ->
            securePrefs.putObject(ConstantPreferences.USER_ACCOUNT, data)
        }
    }

    /** LOGIN */
    fun doLoginByEmail(username: String, password: String) {
        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.loginByEmailState },
                set = { state -> copy(loginByEmailState = state) }
            ),
            block = {
                loginUseCase(
                    LoginRequest(
                        email = username,
                        password = password,
                        deviceId = installationIdProvider.getInstallationId(),
                        createdAt = System.currentTimeMillis().toString(),
                    )
                )
            }
        )
    }

    /** LOGIN BY GOOGLE */
    fun doLoginByGoogle() {
        uiState.update { it.copy(loginByGoogleState = Resource.Success(Unit)) }
    }

    /** LOGIN BY GOOGLE */
    fun doLoginByFacebook() {
        uiState.update { it.copy(loginByFacebookState = Resource.Success(Unit)) }
    }

}