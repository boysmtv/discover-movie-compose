package com.mtv.app.movie.feature.event.home

import com.mtv.app.movie.data.model.response.LoginResponse

data class HomeDataListener(
    val loginResponse: LoginResponse? = null,
)
