package com.mtv.app.movie.data.model.response

import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val userId: String = EMPTY_STRING,
    val userToken: String = EMPTY_STRING,
    val lastLogin: String = EMPTY_STRING
)