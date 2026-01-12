package com.mtv.app.movie.data.datasource

import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.utils.HttpMethod

class ApiEndPoint {

    object GetName : IApiEndPoint {
        override val path = "api/name"
        override val method = HttpMethod.Get
    }

    object AuthLogin : IApiEndPoint {
        override val path = "auth/login"
        override val method = HttpMethod.Post
    }

    object AuthCheck : IApiEndPoint {
        override val path = "auth/check"
        override val method = HttpMethod.Post
    }

    object AuthLogout : IApiEndPoint {
        override val path = "auth/logout"
        override val method = HttpMethod.Post
    }

}