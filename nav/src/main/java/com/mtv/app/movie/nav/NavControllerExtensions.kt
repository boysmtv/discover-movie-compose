package com.mtv.app.movie.nav

import androidx.navigation.NavController

fun NavController.navigateAndPopSplash(route: String) {
    this.navigate(route) {
        popUpTo(AppDestinations.SPLASH_GRAPH) { inclusive = true }
    }
}

fun NavController.navigateAndPopRegister(route: String) {
    this.navigate(route) {
        popUpTo(AppDestinations.REGISTER_GRAPH) { inclusive = true }
    }
}

fun NavController.navigateAndPopLogin(route: String) {
    this.navigate(route) {
        popUpTo(AppDestinations.LOGIN_GRAPH) { inclusive = true }
    }
}

fun NavController.navigateAndPopHome(route: String) {
    this.navigate(route) {
        popUpTo(AppDestinations.HOME_GRAPH) { inclusive = true }
    }
}
