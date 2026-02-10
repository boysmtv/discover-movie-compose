/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SplashContract.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 13.47
 */

package com.mtv.app.movie.feature.contract

import com.mtv.based.core.network.utils.ResourceFirebase

data class SplashStateListener(
    val splashState: ResourceFirebase<String> = ResourceFirebase.Loading,
)

data class SplashEventListener(
    val onDoSplash: () -> Unit
)

data class SplashNavigationListener(
    val onNavigateToLogin: () -> Unit,
)