package com.mtv.app.movie.feature.login

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.feature.login.model.LoginRequest
import com.mtv.app.movie.feature.login.model.LoginResponse
import com.mtv.based.core.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {

    val loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading)

    fun doLogin(username: String, password: String) {
        launchUseCase(loginState) {
            loginUseCase(
                LoginRequest(
                    username = username,
                    password = password
                )
            )
        }
    }
}