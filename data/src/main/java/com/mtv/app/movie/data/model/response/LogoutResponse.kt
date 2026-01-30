/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LogoutResponse.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 14.55
 */

package com.mtv.app.movie.data.model.response

import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponse(
    val createdAt: String = EMPTY_STRING
)