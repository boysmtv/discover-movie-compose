/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedScreen.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 12.10
 */

package com.mtv.app.movie.feature.ui.liked

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.R
import com.mtv.app.movie.feature.event.liked.LikedDataListener
import com.mtv.app.movie.feature.event.liked.LikedEventListener
import com.mtv.app.movie.feature.event.liked.LikedNavigationListener
import com.mtv.app.movie.feature.event.liked.LikedStateListener
import com.mtv.app.movie.feature.utils.previewMovieDetail
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
)
@Composable
fun PreviewLikedScreen() {
    LikedScreen(
        uiState = LikedStateListener(),
        uiData = LikedDataListener(
            movieLikedList = listOf(
                previewMovieDetail,
                previewMovieDetail,
                previewMovieDetail,
                previewMovieDetail,
                previewMovieDetail,
                previewMovieDetail,
            )
        ),
        uiEvent = LikedEventListener({}, {}, {}),
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LikedHeader(
            onDeletedAllMovies = uiEvent.onDeletedAllMovies
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (uiData.movieLikedList.isNotEmpty()) {
            LikedFeaturedSection(
                movies = uiData.movieLikedList,
                onClickedMovies = uiNavigation.onNavigateToMovieDetail
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
)
@Composable
fun PreviewLikedHeader() {
    LikedHeader(
        onDeletedAllMovies = { }
    )
}

@Composable
fun LikedHeader(
    onDeletedAllMovies: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Favorite",
            color = Color.White.copy(0.7f),
            fontSize = 20.sp
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f))
                .clickable { onDeletedAllMovies() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.DeleteSweep,
                contentDescription = "Notification",
                tint = Color.White.copy(0.8f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}