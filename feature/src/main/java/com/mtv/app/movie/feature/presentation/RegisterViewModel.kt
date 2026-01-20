package com.mtv.app.movie.feature.presentation

import androidx.lifecycle.viewModelScope
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.core.provider.utils.device.InstallationIdProvider
import com.mtv.app.movie.common.utils.nowDb
import com.mtv.app.movie.data.model.request.RegisterRequest
import com.mtv.app.movie.domain.user.RegisterUseCase
import com.mtv.based.core.network.utils.ResourceFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val sessionManager: SessionManager,
    private val installationIdProvider: InstallationIdProvider
) : BaseViewModel() {

    val registerState = MutableStateFlow<ResourceFirebase<String>>(ResourceFirebase.Loading)

    init {
        observeRegisterState()
    }

    fun doRegister(name: String, email: String, phone: String, password: String) {
        launchFirebaseUseCase(registerState) {
            registerUseCase(
                RegisterRequest(
                    deviceId = installationIdProvider.getInstallationId(),
                    name = name,
                    email = email,
                    phone = phone,
                    password = password,
                    createdAt = nowDb()
                )
            )
        }
    }

    private fun observeRegisterState() {
        viewModelScope.launch {
            registerState.collect { result ->
                if (result is ResourceFirebase.Success) {
                    sessionManager.saveUid(result.data)
                }
            }
        }
    }

}