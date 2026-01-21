package com.mtv.app.movie.feature.event.login

import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.utils.ResourceFirebase

data class LoginStateListener(
    val loginState: ResourceFirebase<LoginResponse> = ResourceFirebase.Loading,
)
