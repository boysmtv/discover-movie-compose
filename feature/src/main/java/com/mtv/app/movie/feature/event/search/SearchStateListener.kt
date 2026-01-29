package com.mtv.app.movie.feature.event.search

import com.mtv.based.core.network.utils.ResourceFirebase

data class SearchStateListener(
    val splashState: ResourceFirebase<String> = ResourceFirebase.Loading,
)
