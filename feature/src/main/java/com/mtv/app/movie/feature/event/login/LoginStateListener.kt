package com.mtv.app.movie.feature.event.login

import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

data class LoginStateListener(
    val loginByEmailState: ResourceFirebase<LoginResponse> = ResourceFirebase.Loading,
    val loginByGoogleState: Resource<Unit> = Resource.Loading,
    val loginByFacebookState: Resource<Unit> = Resource.Loading,
)
