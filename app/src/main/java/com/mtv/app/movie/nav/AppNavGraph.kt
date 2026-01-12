/*
 * Project: Boys.mtv@gmail.com
 * File: AppNavGraph.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 11.08
 */

package com.mtv.app.movie.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mtv.app.movie.common.nav.AppDestinations

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = AppDestinations.SPLASH
    ) {
        splashGraph(navController)
        loginGraph(navController)
        registerGraph(navController)
        homeGraph(navController)
    }
}