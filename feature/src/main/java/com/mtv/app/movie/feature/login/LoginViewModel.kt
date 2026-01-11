package com.mtv.app.movie.feature.login

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.common.utils.toMap
import com.mtv.app.movie.domain.model.LoginRequest
import com.mtv.app.movie.domain.model.LoginResponse
import com.mtv.app.movie.domain.usecase.LoginFirebaseUseCase
import com.mtv.based.core.network.firebase.result.FirebaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginFirebaseUseCase: LoginFirebaseUseCase<LoginResponse>,
) : BaseViewModel() {

    val loginFirebase = MutableStateFlow<FirebaseResult<LoginResponse>>(FirebaseResult.Loading)

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