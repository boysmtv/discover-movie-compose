package com.mtv.app.movie.data.model.response

import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val uid: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val phone: String = EMPTY_STRING,
    val photo: String = EMPTY_STRING,
    val createdAt: String = EMPTY_STRING
)