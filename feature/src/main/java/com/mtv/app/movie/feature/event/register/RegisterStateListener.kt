package com.mtv.app.movie.feature.event.register

import com.mtv.based.core.network.utils.ResourceFirebase

data class RegisterStateListener(
    val registerState: ResourceFirebase<String> = ResourceFirebase.Loading,
)
