package com.mtv.app.movie.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mtv.app.movie.common.nav.AppDestinations
import com.mtv.app.movie.feature.route.HomeRoute

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(BottomNavItem.Home.route) {
            HomeRoute(
                navController
            )
        }

        composable(BottomNavItem.Play.route) {
//            SearchScreen()
        }

        composable(BottomNavItem.Search.route) {
//            SearchScreen()
        }

        composable(BottomNavItem.Profile.route) {
//            ProfileScreen()
        }

        composable(AppDestinations.LOGIN) {
//            ProfileScreen()
        }
    }
}
