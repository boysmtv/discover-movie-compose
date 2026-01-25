package com.mtv.app.movie.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.feature.route.movie.MovieDetailRoute
import com.mtv.app.movie.feature.route.HomeRoute
import com.mtv.app.movie.feature.route.LoginRoute
import com.mtv.app.movie.feature.route.RegisterRoute
import com.mtv.app.movie.feature.route.SplashRoute
import com.mtv.app.movie.feature.route.movie.PlayMovieRoute

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

fun NavGraphBuilder.detailMovieGraph(navController: NavHostController) {
    composable(
        route = AppDestinations.MOVIE_DETAIL_ROUTE,
        arguments = listOf(
            navArgument(Constant.SharedParam.MOVIE_ID) {
                type = NavType.IntType
            }
        )
    ) {
        MovieDetailRoute(navController = navController)
    }
}

fun NavGraphBuilder.playMovieGraph(navController: NavHostController) {
    composable(
        route = AppDestinations.MOVIE_PLAY_ROUTE,
        arguments = listOf(
            navArgument(Constant.SharedParam.VIDEOS_KEY) {
                type = NavType.StringType
            }
        )
    ) {
        PlayMovieRoute(navController = navController)
    }
}
