package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.data.model.request.CheckRequest
import com.mtv.app.movie.data.model.request.LogoutRequest
import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.app.movie.domain.usecase.CheckUseCase
import com.mtv.app.movie.domain.usecase.LogoutUseCase
import com.mtv.based.core.network.utils.ResourceFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val checkUseCase: CheckUseCase<CheckResponse>,
    private val logoutUseCase: LogoutUseCase<LogoutResponse>
) : BaseViewModel() {

    val checkState = MutableStateFlow<ResourceFirebase<CheckResponse>>(ResourceFirebase.Loading)
    val logoutState = MutableStateFlow<ResourceFirebase<LogoutResponse>>(ResourceFirebase.Loading)

    fun doCheck(email: String) {
        launchFirebaseUseCase(checkState) {
            checkUseCase(
                CheckRequest(
                    email = email
                ).toMap()
            )
        }
    }

    fun doLogout(email: String) {
        launchFirebaseUseCase(logoutState) {
            logoutUseCase(
                LogoutRequest(
                    email = email
                ).toMap()
            )
        }
    }
}