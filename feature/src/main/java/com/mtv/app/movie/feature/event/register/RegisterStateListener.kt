package com.mtv.app.movie.feature.event.register

import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

data class RegisterStateListener(
    val registerByEmailState: ResourceFirebase<String> = ResourceFirebase.Loading,
    val registerByGoogleState: Resource<Unit> = Resource.Loading,
    val registerByFacebookState: Resource<Unit> = Resource.Loading,
    val activeDialog: RegisterDialog? = null
)
