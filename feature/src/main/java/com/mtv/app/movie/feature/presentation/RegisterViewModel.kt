package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.core.provider.utils.device.InstallationIdProvider
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.utils.nowDb
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.RegisterRequest
import com.mtv.app.movie.domain.user.RegisterUseCase
import com.mtv.app.movie.feature.event.register.RegisterStateListener
import com.mtv.app.movie.feature.event.splash.SplashStateListener
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val sessionManager: SessionManager,
    private val installationIdProvider: InstallationIdProvider
) : BaseViewModel(), UiOwner<RegisterStateListener, Unit> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(RegisterStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(Unit)

    init {
        collectFieldSuccess(
            parent = uiState,
            selector = { it.registerState }
        ) { data ->
            sessionManager.saveUid(data)
        }
    }

    fun doRegister(name: String, email: String, phone: String, password: String) =
        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.registerState },
                set = { state -> copy(registerState = state) }
            ),
            block = {
                registerUseCase(
                    RegisterRequest(
                        uid = EMPTY_STRING,
                        name = name,
                        email = email,
                        phone = phone,
                        password = password,
                        deviceId = installationIdProvider.getInstallationId(),
                        createdAt = System.currentTimeMillis().toString()
                    )
                )
            }
        )

}