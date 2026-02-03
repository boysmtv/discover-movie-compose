/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: ProfileResponse.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 12.25
 */

package com.mtv.app.movie.data.model.response

import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val uid: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val phone: String = EMPTY_STRING,
    val photoUrl: String = EMPTY_STRING,
    val createdAt: String = EMPTY_STRING
)