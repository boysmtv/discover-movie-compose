package com.mtv.app.movie.data.datasource.remote

import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.utils.HttpMethod

class TmdbApi {

    object GetName : IApiEndPoint {
        override val path = "api/name"
        override val method = HttpMethod.Get
    }

}