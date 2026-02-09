/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: PasswordRequest.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 16.41
 */

package com.mtv.app.movie.data.model.request

data class PasswordRequest(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)
