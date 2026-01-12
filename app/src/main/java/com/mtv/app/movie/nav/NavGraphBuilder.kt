package com.mtv.app.movie.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mtv.app.movie.common.nav.AppDestinations
import com.mtv.app.movie.feature.route.HomeRoute
import com.mtv.app.movie.feature.route.LoginRoute
import com.mtv.app.movie.feature.ui.home.HomeScreen
import com.mtv.app.movie.feature.ui.login.LoginScreen
import com.mtv.app.movie.feature.route.RegisterRoute
import com.mtv.app.movie.feature.route.SplashRoute

fun NavGraphBuilder.splashGraph(navController: NavHostController) {
    composable(AppDestinations.SPLASH) {
        SplashRoute(navController)
    }
}

fun NavGraphBuilder.loginGraph(navController: NavController) {
    composable(AppDestinations.LOGIN) {
        LoginRoute(navController)
    }
}

fun NavGraphBuilder.registerGraph(navController: NavController) {
    composable(AppDestinations.REGISTER) {
        RegisterRoute(navController)
    }
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable(AppDestinations.HOME) {
        HomeRoute(navController)
    }
}
