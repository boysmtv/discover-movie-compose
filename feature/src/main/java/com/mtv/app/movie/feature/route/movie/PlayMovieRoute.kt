package com.mtv.app.movie.feature.route.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.movie.common.based.BaseScreen
import com.mtv.app.movie.feature.presentation.PlayViewModel
import com.mtv.app.movie.feature.ui.play.PlayMovieScreen

@Composable
fun PlayMovieRoute(
    navController: NavController,
    viewModel: PlayViewModel = hiltViewModel(),
) {

    val baseUiState by viewModel.baseUiState.collectAsState()

    BaseScreen(
        baseUiState = baseUiState,
        onDismissError = viewModel::dismissError
    ) {
        PlayMovieScreen(
            key = viewModel.getKey(),
            onBack = {
                navController.popBackStack()
            }
        )
    }
}