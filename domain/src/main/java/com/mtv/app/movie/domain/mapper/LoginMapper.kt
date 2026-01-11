package com.mtv.app.movie.domain.mapper

import com.mtv.app.movie.domain.model.LoginResponse

fun Map<String, Any>.toLoginResponse(): LoginResponse {
    return LoginResponse(
        name = this["name"] as? String ?: "",
        email = this["email"] as? String ?: "",
        phone = this["phone"] as? String ?: "",
        createdAt = this["createdAt"] as? String ?: "",
    )
}
