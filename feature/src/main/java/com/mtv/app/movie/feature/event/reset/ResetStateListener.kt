package com.mtv.app.movie.feature.event.reset

import com.mtv.based.core.network.utils.ResourceFirebase

data class ResetStateListener(
    val resetPasswordState: ResourceFirebase<Unit> = ResourceFirebase.Loading,
)
