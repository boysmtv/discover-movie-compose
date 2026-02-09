/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: PasswordContract.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 16.01
 */

package com.mtv.app.movie.feature.event.profile

import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.utils.Resource

data class PasswordStateListener(
    val onSubmitPasswordState: Resource<Unit> = Resource.Loading,
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
    object Success : PasswordDialog()
}
