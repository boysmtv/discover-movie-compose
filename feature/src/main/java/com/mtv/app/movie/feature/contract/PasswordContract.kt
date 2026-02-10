/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: PasswordContract.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 16.01
 */

package com.mtv.app.movie.feature.contract

import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.utils.ResourceFirebase

data class PasswordStateListener(
    val onSubmitPasswordState: ResourceFirebase<Unit> = ResourceFirebase.Loading,
    val activeDialog: PasswordDialog? = null
)

data class PasswordDataListener(
    val userAccount: LoginResponse? = null,
)

data class PasswordEventListener(
    val onSubmitPassword: (
        password: String,
        newPassword: String,
        newPasswordConfirm: String
    ) -> Unit,
    val onDismissActiveDialog: () -> Unit,
)

data class PasswordNavigationListener(
    val onBack: () -> Unit = {},
)

sealed class PasswordDialog {
    data class Validate(
        val message: String
    ) : PasswordDialog()

    object Success : PasswordDialog()
}
