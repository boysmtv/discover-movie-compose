package com.mtv.app.movie.feature.event.profile

import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

data class ProfileStateListener(
    val profileState: ResourceFirebase<LoginResponse> = ResourceFirebase.Loading,
    val logoutState: Resource<Unit> = Resource.Loading
)
