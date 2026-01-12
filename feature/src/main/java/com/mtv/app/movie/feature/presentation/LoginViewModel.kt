package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.domain.usecase.LoginUseCase
import com.mtv.based.core.network.utils.ResourceFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginFirebaseUseCase: LoginUseCase<LoginResponse>,
) : BaseViewModel() {

    val loginFirebase = MutableStateFlow<ResourceFirebase<LoginResponse>>(ResourceFirebase.Loading)

    fun doLoginFirebase(username: String, password: String) {
        launchFirebaseUseCase(loginFirebase) {
            loginFirebaseUseCase(
                LoginRequest(
                    name = username,
                    password = password
                ).toMap()
            )
        }
    }
}