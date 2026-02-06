/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedScreen.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 12.10
 */

package com.mtv.app.movie.feature.ui.liked

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.common.DeleteTarget
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.StateMovieResult
import com.mtv.app.movie.feature.event.liked.LikedDataListener
import com.mtv.app.movie.feature.event.liked.LikedEventListener
import com.mtv.app.movie.feature.event.liked.LikedNavigationListener
import com.mtv.app.movie.feature.event.liked.LikedStateListener
import com.mtv.app.movie.feature.utils.previewMovieDetail
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.WARNING_STRING

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_3
)
@Composable
fun PreviewLikedScreen() {
    LikedScreen(
        uiState = LikedStateListener(),
        uiData = LikedDataListener(
            movieLikedList = List(12) { index ->
                previewMovieDetail.copy(
                    id = index
                )
            }
        ),
        uiEvent = LikedEventListener({}, {}, {}, {}),
        uiNavigation = LikedNavigationListener {},
    )
}

@Composable
fun LikedScreen(
    uiState: LikedStateListener,
    uiData: LikedDataListener,
    uiEvent: LikedEventListener,
    uiNavigation: LikedNavigationListener
) {

    if (
        uiState.stateMovieResult is StateMovieResult.Success &&
        uiState.deleteSource == DeleteTarget.ALL
    ) {
        DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.SUCCESS,
                title = stringResource(R.string.success),
                message = stringResource(R.string.success_clear_fav),
                primaryButtonText = OK_STRING
            ),
            onDismiss = { uiEvent.onDismissDeleteMovie() }
        )
    }

    (uiState.stateMovieResult as? StateMovieResult.Error)?.let {
        DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.ERROR,
                title = WARNING_STRING,
                message = "Error Message : ${it.message}",
                primaryButtonText = OK_STRING
            ),
            onDismiss = { uiEvent.onDismissDeleteMovie() }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF181818),
                        Color(0xFF0F0F0F),
                        Color(0xFF000000)
                    )
                )
            )
    ) {
        LikedHeader(
            onDeletedAllMovies = uiEvent.onDeletedAllMovies
        )

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(
            visible = uiData.movieLikedList.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            LikedFeaturedSection(
                movies = uiData.movieLikedList,
                onClickDetail = uiNavigation.onNavigateToMovieDetail,
                onClickDeleted = uiEvent.onDeletedMovie
            )
        }
    }

}