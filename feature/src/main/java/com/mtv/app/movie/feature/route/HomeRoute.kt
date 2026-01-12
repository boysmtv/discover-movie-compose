package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.common.nav.AppDestinations
import com.mtv.app.movie.feature.presentation.HomeViewModel
import com.mtv.app.movie.feature.ui.home.HomeScreen
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1

@Composable
fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val checkState by viewModel.checkState.collectAsState()
    val logoutState by viewModel.logoutState.collectAsState()
    val baseUiState by viewModel.baseUiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        HomeScreen(
            checkState = checkState,
            logoutState = logoutState,
            onDoCheck = { email -> viewModel.doCheck(email) },
            onDoLogout = { email -> viewModel.doLogout(email) },
            onSuccessLogout = {
                navController.navigate(AppDestinations.LOGIN) {
                    popUpTo(AppDestinations.LOGIN) { inclusive = true }
                }
            }
        )
    }

}