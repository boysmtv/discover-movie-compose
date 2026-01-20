package com.mtv.app.movie.config

import com.mtv.app.movie.common.BuildConfig
import com.mtv.based.core.network.config.AppNetworkConfig
import com.mtv.based.core.network.config.NetworkConfig
import com.mtv.based.core.network.config.NetworkConfigProvider

class AppNetworkConfigProvider : NetworkConfigProvider {
    override fun provide(): NetworkConfig =
        AppNetworkConfig(
            baseUrl = BuildConfig.TMDB_URL,
            useKtor = BuildConfig.USE_KTOR,
            isDebug = BuildConfig.DEBUG
        )
}
