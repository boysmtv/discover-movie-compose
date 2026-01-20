package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.device.DeviceInfoProvider
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.data.model.request.SplashRequest
import com.mtv.app.movie.domain.user.SplashUseCase
import com.mtv.based.core.network.utils.ResourceFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase,
    private val deviceInfoProvider: DeviceInfoProvider
) : BaseViewModel() {

    val splashState = MutableStateFlow<ResourceFirebase<String>>(ResourceFirebase.Loading)

    fun doSplash() {
        launchFirebaseUseCase(splashState) {
            splashUseCase(
                SplashRequest(
                    initDate = "Today",
                    deviceInfo = deviceInfoProvider.getAllDeviceInfo()
                ).toMap()
            )
        }
    }

}