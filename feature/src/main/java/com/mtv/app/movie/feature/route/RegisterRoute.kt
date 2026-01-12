package com.mtv.app.movie.feature.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.common.nav.AppDestinations
import com.mtv.app.movie.feature.presentation.RegisterViewModel
import com.mtv.app.movie.feature.ui.register.RegisterScreen
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1

@Composable
fun RegisterRoute(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val registerState by viewModel.registerState.collectAsState()
    val baseUiState by viewModel.baseUiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        RegisterScreen(
            registerState = registerState,
            onRegisterClick = { name, email, phone, password ->
                viewModel.doRegister(
                    name, email, phone, password
                )
            },
            onRegisterSuccess = {
                navController.navigate(AppDestinations.LOGIN) {
                    popUpTo(AppDestinations.LOGIN) { inclusive = true }
                }
            }
        )
    }
}