package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.common.nav.AppDestinations
import com.mtv.app.movie.feature.presentation.LoginViewModel
import com.mtv.app.movie.feature.ui.login.LoginScreen
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1

@Composable
fun LoginRoute(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val loginState by viewModel.loginState.collectAsState()
    val baseUiState by viewModel.baseUiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        LoginScreen(
            loginState = loginState,
            onDoLogin = { username, password -> viewModel.doLogin(username, password) },
            onSuccessLogin = {
                navController.navigate(AppDestinations.HOME) {
                    popUpTo(AppDestinations.HOME) { inclusive = true }
                }
            }
        )
    }

}