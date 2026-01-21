package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.device.DeviceInfoProvider
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.LoginRequest
import com.mtv.app.movie.data.model.request.SplashRequest
import com.mtv.app.movie.domain.user.SplashUseCase
import com.mtv.app.movie.feature.event.login.LoginStateListener
import com.mtv.app.movie.feature.event.splash.SplashStateListener
import com.mtv.based.core.network.utils.ResourceFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase,
    private val deviceInfoProvider: DeviceInfoProvider
) : BaseViewModel() {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    private val _uiState = MutableStateFlow(SplashStateListener())
    val uiState: StateFlow<SplashStateListener> = _uiState

    /** SPLASH */
    fun doSplash() {
        launchFirebaseUseCase(
            target = _uiState.valueFlowOf(
                get = { it.splashState },
                set = { state -> copy(splashState = state) }
            ),
            block = {
                splashUseCase(
                    SplashRequest(
                        initDate = "Today",
                        deviceInfo = deviceInfoProvider.getAllDeviceInfo()
                    ).toMap()
                )
            }
        )
    }

}