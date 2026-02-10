/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: ResetContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 13.49
 */

package com.mtv.app.movie.feature.contract

import com.mtv.based.core.network.utils.ResourceFirebase

data class ResetStateListener(
    val resetPasswordState: ResourceFirebase<Unit> = ResourceFirebase.Loading,
)

data class ResetEventListener(
    val onResetPasswordClicked: (email: String) -> Unit
)

data class ResetNavigationListener(
    val onNavigateToLogin: () -> Unit,
    val onRegisterByGoogle: () -> Unit,
    val onRegisterByFacebook: () -> Unit,
)