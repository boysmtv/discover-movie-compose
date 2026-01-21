package com.mtv.app.movie.feature.event.splash

import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.utils.ResourceFirebase

data class SplashStateListener(
    val splashState: ResourceFirebase<String> = ResourceFirebase.Loading,
)
