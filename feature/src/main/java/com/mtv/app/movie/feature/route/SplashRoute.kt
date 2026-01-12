package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.common.nav.AppDestinations
import com.mtv.app.movie.feature.presentation.SplashViewModel
import com.mtv.app.movie.feature.ui.splash.SplashScreen
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1

@Composable
fun SplashRoute(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val splashState by viewModel.splashState.collectAsState()
    val baseUiState by viewModel.baseUiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        SplashScreen(
            splashState = splashState,
            onDoSplash = viewModel::doSplash,
            onSuccessSplash = {
                navController.navigate(AppDestinations.REGISTER) {
                    popUpTo(AppDestinations.REGISTER) { inclusive = true }
                }
            }
        )
    }

}