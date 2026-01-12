/*
 * Project: Boys.mtv@gmail.com
 * File: LogoutResponse.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 14.55
 */

package com.mtv.app.movie.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponse(
    val lastLogout: String,
)