package com.mtv.app.movie.data.model.request

import com.mtv.app.core.provider.utils.device.DeviceInfo
import kotlinx.serialization.Serializable

@Serializable
data class SplashRequest(
    val initDate: String,
    val deviceInfo: DeviceInfo,
)