package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.core.provider.utils.device.InstallationIdProvider
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.RegisterRequest
import com.mtv.app.movie.domain.user.RegisterUseCase
import com.mtv.app.movie.feature.contract.RegisterDialog
import com.mtv.app.movie.feature.contract.RegisterStateListener
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
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

    /** REGISTER BY EMAIL */
    fun doRegisterByEmail(name: String, email: String, phone: String, password: String, photoBase64: String) =
        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.registerByEmailState },
                set = { state -> copy(registerByEmailState = state) }
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
                        photo = photoBase64,
                        createdAt = System.currentTimeMillis().toString()
                    )
                )
            },
            onSuccess = { data ->
                sessionManager.saveUid(data)
                uiState.update { it.copy(activeDialog = RegisterDialog.Success) }
            }
        )

    /** REGISTER BY GOOGLE */
    fun doLoginByGoogle() {
        uiState.update {
            it.copy(
                registerByGoogleState = Resource.Success(Unit),
                activeDialog = RegisterDialog.Maintenance()
            )
        }
    }

    /** REGISTER BY GOOGLE */
    fun doLoginByFacebook() {
        uiState.update {
            it.copy(
                registerByFacebookState = Resource.Success(Unit),
                activeDialog = RegisterDialog.Maintenance()
            )
        }
    }

    fun doDismissActiveDialog() {
        uiState.update {
            it.copy(
                activeDialog = null,
                registerByFacebookState = Resource.Loading,
                registerByGoogleState = Resource.Loading
            )
        }
    }

}