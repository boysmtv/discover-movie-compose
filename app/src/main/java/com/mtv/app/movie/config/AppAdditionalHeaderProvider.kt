package com.mtv.app.movie.config

import com.mtv.based.core.network.header.AdditionalHeaderProvider
import javax.inject.Inject

class AppAdditionalHeaderProvider @Inject constructor() :
    AdditionalHeaderProvider {

    override fun provide(): Map<String, String> =
        mapOf(
            "Authorization" to "Bearer eyJhbGciOiJIUzI1",
            "X-Device-Id" to "1234567890",
            "X-App-Name" to "MovieApp",
            "X-Version" to "1.0.0",
            "Accept" to "application/json",
            "Accept-Language" to "id-ID"
        )
}