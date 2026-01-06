/*
 * Project: Boys.mtv@gmail.com
 * File: AppNavGraph.kt
 *
 * Last modified by Dedy Wijaya on 31/12/2025 11.08
 */

package com.mtv.app.movie

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mtv.app.movie.feature.home.ui.HomeScreen
import com.mtv.app.movie.feature.login.LoginScreen
import com.mtv.app.movie.feature.register.RegisterRoute

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("register") {
            RegisterRoute(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
    }
}
