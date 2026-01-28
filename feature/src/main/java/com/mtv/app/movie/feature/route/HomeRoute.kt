package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mtv.app.movie.common.BaseRoute
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.event.home.HomeDataListener
import com.mtv.app.movie.feature.event.home.HomeEventListener
import com.mtv.app.movie.feature.event.home.HomeNavigationListener
import com.mtv.app.movie.feature.event.home.HomeStateListener
import com.mtv.app.movie.feature.presentation.HomeViewModel
import com.mtv.app.movie.feature.ui.home.HomeScreen
import com.mtv.app.movie.nav.AppDestinations
import com.mtv.app.movie.nav.navigateAndPopHome

@Composable
fun HomeRoute(navController: NavController) {
    BaseRoute<HomeViewModel, HomeStateListener, HomeDataListener> { vm, base, uiState, uiData ->
        BaseScreen(baseUiState = base, onDismissError = vm::dismissError) {
            HomeScreen(
                uiState = uiState,
                uiData = uiData,
                uiEvent = homeEvent(vm),
                uiNavigation = homeNavigation(navController)
            )
        }
    }
}

private fun homeEvent(vm: HomeViewModel) = HomeEventListener(
    onCheck = vm::doCheck,
    onLogout = vm::doLogout,
    onLoadMovies = {
        vm.getNowPlaying()
        vm.getPopular()
        vm.getTopRated()
        vm.getUpComing()
    }
)

private fun homeNavigation(nav: NavController) = HomeNavigationListener(
    onNavigateToLogin = {
        nav.navigateAndPopHome(AppDestinations.LOGIN_GRAPH)
    },
    onNavigateToMovieDetail = { movie ->
        nav.navigate(AppDestinations.navigateToDetailMovies(movie.id))
    }
)
