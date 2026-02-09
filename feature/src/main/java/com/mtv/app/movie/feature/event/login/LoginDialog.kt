/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LoginDialog.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 13.32
 */

package com.mtv.app.movie.feature.event.login

sealed class LoginDialog {
    data class Maintenance(
        val message: String
    ) : LoginDialog()
}
