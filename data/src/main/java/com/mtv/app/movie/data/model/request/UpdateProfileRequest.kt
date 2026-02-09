/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: UpdateProfileRequest.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 12.01
 */

package com.mtv.app.movie.data.model.request

data class UpdateProfileRequest(
    val uid: String,
    val name: String,
    val phone: String,
    val photo: String,
    val email: String,
    val password: String
)
