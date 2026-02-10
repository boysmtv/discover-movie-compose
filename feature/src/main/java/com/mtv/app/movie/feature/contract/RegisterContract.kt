/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: RegisterContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 13.49
 */

package com.mtv.app.movie.feature.contract

import com.mtv.app.movie.common.Constant.Title.UNDER_MAINTENANCE
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

data class RegisterStateListener(
    val registerByEmailState: ResourceFirebase<String> = ResourceFirebase.Loading,
    val registerByGoogleState: Resource<Unit> = Resource.Loading,
    val registerByFacebookState: Resource<Unit> = Resource.Loading,
    val activeDialog: RegisterDialog? = null
)

data class RegisterEventListener(
    val onRegisterByEmailClicked: (name: String, email: String, phone: String, password: String, photoBase64: String) -> Unit,
    val onRegisterByGoogleClicked: () -> Unit,
    val onRegisterByFacebookClicked: () -> Unit,
    val onDismissActiveDialog: () -> Unit
)

data class RegisterNavigationListener(
    val onNavigateToLogin: () -> Unit,
)

sealed class RegisterDialog {
    data class Maintenance(
        val message: String = UNDER_MAINTENANCE
    ) : RegisterDialog()

    object Success : RegisterDialog()
}
