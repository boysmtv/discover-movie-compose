package com.mtv.app.movie.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mtv.app.movie.feature.route.HomeRoute
import com.mtv.app.movie.feature.route.LoginRoute
import com.mtv.app.movie.feature.route.RegisterRoute
import com.mtv.app.movie.feature.route.SplashRoute

fun NavGraphBuilder.splashGraph(navController: NavHostController) {
    composable(AppDestinations.SPLASH_GRAPH) {
        SplashRoute(navController)
    }
}

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    composable(AppDestinations.LOGIN_GRAPH) {
        LoginRoute(navController)
    }

    composable(AppDestinations.REGISTER_GRAPH) {
        RegisterRoute(navController)
    }
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(
        startDestination = BottomNavItem.Home.route,
        route = AppDestinations.HOME_GRAPH
    ) {
        composable(BottomNavItem.Home.route) {
            HomeRoute(navController)
        }
        composable(BottomNavItem.Play.route) {

        }
        composable(BottomNavItem.Search.route) {
//            SearchScreenRoute(navController)
        }
        composable(BottomNavItem.Profile.route) {
//            ProfileScreenRoute(navController)
        }
    }
}
