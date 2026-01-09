package com.mtv.app.movie.data.datasource.remote

import com.mtv.based.core.network.endpoint.IApiEndPoint
import com.mtv.based.core.network.utils.HttpMethod

class FirebaseApi {

    object Register : IApiEndPoint {
        override val path = "auth/register"
        override val method = HttpMethod.Post
    }

    object Login : IApiEndPoint {
        override val path = "auth/login"
        override val method = HttpMethod.Post
    }

    object Profile : IApiEndPoint {
        override val path = "auth/profile"
        override val method = HttpMethod.Post
    }

}
