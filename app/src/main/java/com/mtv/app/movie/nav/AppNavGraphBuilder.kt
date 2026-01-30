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
import com.mtv.app.movie.feature.route.LikedRoute
import com.mtv.app.movie.feature.route.LoginRoute
import com.mtv.app.movie.feature.route.ProfileRoute
import com.mtv.app.movie.feature.route.RegisterRoute
import com.mtv.app.movie.feature.route.ResetRoute
import com.mtv.app.movie.feature.route.SplashRoute
import com.mtv.app.movie.feature.route.movie.PlayMovieRoute
import com.mtv.app.movie.feature.route.SearchRoute

fun NavGraphBuilder.splashGraph(nav: NavHostController) {
    composable(AppDestinations.SPLASH_GRAPH) {
        SplashRoute(nav)
    }
}

fun NavGraphBuilder.authGraph(nav: NavHostController) {
    composable(AppDestinations.LOGIN_GRAPH) {
        LoginRoute(nav)
    }

    composable(AppDestinations.REGISTER_GRAPH) {
        RegisterRoute(nav)
    }

    composable(AppDestinations.RESET_GRAPH) {
        ResetRoute(nav)
    }
}

fun NavGraphBuilder.homeGraph(nav: NavHostController) {
    navigation(
        startDestination = BottomNavItem.Home.route,
        route = AppDestinations.HOME_GRAPH
    ) {
        composable(BottomNavItem.Home.route) {
            HomeRoute(nav)
        }
        composable(BottomNavItem.Search.route) {
            SearchRoute(nav)
        }
        composable(BottomNavItem.Like.route) {
            LikedRoute(nav)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileRoute(nav)
        }
    }
}

fun NavGraphBuilder.detailMovieGraph(nav: NavHostController) {
    composable(
        route = AppDestinations.MOVIE_DETAIL_ROUTE,
        arguments = listOf(
            navArgument(Constant.SharedParam.MOVIE_ID) {
                type = NavType.IntType
            }
        )
    ) {
        MovieDetailRoute(nav = nav)
    }
}

fun NavGraphBuilder.playMovieGraph(nav: NavHostController) {
    composable(
        route = AppDestinations.MOVIE_PLAY_ROUTE,
        arguments = listOf(
            navArgument(Constant.SharedParam.VIDEOS_KEY) {
                type = NavType.StringType
            }
        )
    ) {
        PlayMovieRoute(nav = nav)
    }
}
