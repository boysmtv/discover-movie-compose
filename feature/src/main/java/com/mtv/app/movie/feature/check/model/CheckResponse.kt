/*
 * Project: Boys.mtv@gmail.com
 * File: CheckResponse.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 14.55
 */

package com.mtv.app.movie.feature.check.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckResponse(
    val lastCheckin: String,
)