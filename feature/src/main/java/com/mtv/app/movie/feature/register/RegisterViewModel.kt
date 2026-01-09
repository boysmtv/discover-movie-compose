package com.mtv.app.movie.feature.register

import androidx.lifecycle.viewModelScope
import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SessionManager
import com.mtv.app.movie.domain.model.RegisterRequest
import com.mtv.app.movie.domain.usecase.RegisterUseCase
import com.mtv.based.core.network.firebase.result.FirebaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    val registerState = MutableStateFlow<FirebaseResult<String>>(FirebaseResult.Loading)

    fun doRegister(name: String, email: String, phone: String, password: String) {
        launchFirebaseUseCase(registerState) {
            registerUseCase(
                RegisterRequest(
                    name = name,
                    email = email,
                    phone = phone,
                    password = password,
                    createdAt = com.google.firebase.Timestamp.now().toString()
                )
            )
        }

        viewModelScope.launch {
            registerState.collect { result ->
                if (result is FirebaseResult.Success) {
                    sessionManager.saveUid(result.data)
                }
            }
        }
    }


}