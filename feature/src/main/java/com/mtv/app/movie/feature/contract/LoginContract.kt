/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LoginContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 13.46
 */

package com.mtv.app.movie.feature.contract

import com.mtv.app.movie.common.Constant.Title.UNDER_MAINTENANCE
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

data class LoginStateListener(
    val loginByEmailState: ResourceFirebase<LoginResponse> = ResourceFirebase.Loading,
    val loginByGoogleState: Resource<Unit> = Resource.Loading,
    val loginByFacebookState: Resource<Unit> = Resource.Loading,
    val activeDialog: LoginDialog? = null
)

data class LoginEventListener(
    val onLoginByEmailClicked: (username: String, password: String) -> Unit,
    val onLoginByGoogleClicked: () -> Unit,
    val onLoginByFacebookClicked: () -> Unit,
    val onDismissActiveDialog: () -> Unit,
)

data class LoginNavigationListener(
    val onNavigateToHome: () -> Unit,
    val onNavigateToSignUpByEmail: () -> Unit,
    val onNavigateToForgotPassword: () -> Unit,
)

sealed class LoginDialog {
    data class Maintenance(
        val message: String = UNDER_MAINTENANCE
    ) : LoginDialog()
}
