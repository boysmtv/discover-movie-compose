package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.device.DeviceInfoProvider
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.SplashRequest
import com.mtv.app.movie.domain.user.SplashUseCase
import com.mtv.app.movie.feature.contract.SplashStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase,
    private val deviceInfoProvider: DeviceInfoProvider
) : BaseViewModel(), UiOwner<SplashStateListener, Unit>  {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(SplashStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(Unit)

    /** SPLASH */
    fun doSplash() {
        launchFirebaseUseCase(
            target = uiState.valueFlowOf(
                get = { it.splashState },
                set = { state -> copy(splashState = state) }
            ),
            block = {
                splashUseCase(
                    SplashRequest(
                        deviceId = deviceInfoProvider.getAllDeviceInfo().deviceId.orEmpty(),
                        createdAt = System.currentTimeMillis().toString(),
                        deviceInfo = deviceInfoProvider.getAllDeviceInfo()
                    )
                )
            },
            loading = false,
        )
    }

}