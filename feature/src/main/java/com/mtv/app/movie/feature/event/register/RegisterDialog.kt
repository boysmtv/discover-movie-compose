/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: RegisterDialog.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 13.42
 */

package com.mtv.app.movie.feature.event.register

sealed class RegisterDialog {
    data class Maintenance(
        val message: String
    ) : RegisterDialog()

    object Register : RegisterDialog()
}
