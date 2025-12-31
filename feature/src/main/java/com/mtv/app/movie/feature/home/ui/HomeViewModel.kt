package com.mtv.app.movie.feature.home.ui

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.movie.feature.check.CheckUseCase
import com.mtv.app.movie.feature.check.model.CheckRequest
import com.mtv.app.movie.feature.check.model.CheckResponse
import com.mtv.app.movie.feature.logout.LogoutUseCase
import com.mtv.app.movie.feature.logout.model.LogoutRequest
import com.mtv.app.movie.feature.logout.model.LogoutResponse
import com.mtv.based.core.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val checkUseCase: CheckUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    val checkState = MutableStateFlow<Resource<CheckResponse>>(Resource.Loading)
    val logoutState = MutableStateFlow<Resource<LogoutResponse>>(Resource.Loading)

    fun check(username: String) {
        launchUseCase(checkState) {
            checkUseCase(
                CheckRequest(username)
            )
        }
    }

    fun logout(username: String) {
        launchUseCase(logoutState) {
            logoutUseCase(
                LogoutRequest(username)
            )
        }
    }
}