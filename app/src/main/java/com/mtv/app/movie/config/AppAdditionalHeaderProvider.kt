package com.mtv.app.movie.config

import com.mtv.based.core.network.header.AdditionalHeaderProvider
import javax.inject.Inject

class AppAdditionalHeaderProvider @Inject constructor() :
    AdditionalHeaderProvider {

    override fun provide(): Map<String, String> =
        mapOf(
            "Authorization" to "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZmIzYTg2OWRkYTIyNjRjNDQ4MjYxY2Q4YWRlMjExMCIsIm5iZiI6MTY2MDA0NTA3My44ODgsInN1YiI6IjYyZjI0NzExMTUxMWFhMDA3YTdjZjNlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.B_f1VYeJoWUsgw6h2T91pfBIgLkIOwogLR7k6dxM4pU",
            "X-Device-Id" to "1234567890",
            "X-App-Name" to "MovieApp",
            "X-Version" to "1.0.0",
            "Accept" to "application/json",
            "Accept-Language" to "id-ID"
        )
}