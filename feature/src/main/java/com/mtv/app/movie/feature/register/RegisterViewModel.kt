package com.mtv.app.movie.feature.register

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.domain.model.RegisterRequest
import com.mtv.app.movie.domain.model.RegisterResponse
import com.mtv.app.movie.domain.usecase.RegisterUseCase
import com.mtv.based.core.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    val registerState = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading)

    fun doRegister(name: String, email: String, phone: String, password: String) {
        launchUseCase(registerState) {
            registerUseCase(
                RegisterRequest(
                    name = name,
                    email = email,
                    phone = phone,
                    password = password,
                )
            )
        }
    }

}