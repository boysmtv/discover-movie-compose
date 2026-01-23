package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.core.provider.utils.device.InstallationIdProvider
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.domain.user.LoginUseCase
import com.mtv.app.movie.feature.event.login.LoginStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase<LoginResponse>,
    private val installationIdProvider: InstallationIdProvider,
    private val securePrefs: SecurePrefs
) : BaseViewModel() {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    private val _uiState = MutableStateFlow(LoginStateListener())
    val uiState: StateFlow<LoginStateListener> = _uiState

    /** INIT */
    init {
        collectFieldSuccess(
            parent = _uiState,
            selector = { it.loginState }
        ) { data ->
            securePrefs.putObject(ConstantPreferences.USER_ACCOUNT, data)
        }
    }

    /** LOGIN */
    fun doLogin(username: String, password: String) {
        launchFirebaseUseCase(
            target = _uiState.valueFlowOf(
                get = { it.loginState },
                set = { state -> copy(loginState = state) }
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

}