package com.mtv.app.movie.feature.ui.detail

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.formatDateAutoLegacy
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.data.model.movie.MovieVideoResponse
import com.mtv.app.movie.feature.contract.DetailDialog
import com.mtv.app.movie.feature.contract.DetailEventListener
import com.mtv.app.movie.feature.contract.DetailNavigationListener
import com.mtv.app.movie.feature.contract.DetailStateListener
import com.mtv.app.movie.feature.utils.previewMovieDetail
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.ERROR_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.WARNING_STRING

@Composable
fun DetailMovieScreen(
    uiState: DetailStateListener,
    uiEvent: DetailEventListener,
    uiNavigation: DetailNavigationListener,
) {
    LaunchedEffect(Unit) { uiEvent.onLoadMovies() }

    LaunchedEffect(uiState.videosState) {
        val key = (uiState.videosState as? Resource.Success)?.data?.results?.firstOrNull()?.key
        if (!key.isNullOrEmpty()) {
            uiNavigation.onNavigateToPlayMovie(key)
            uiEvent.onConsumePlayEvent()
        }
    }

    uiState.activeDialog?.let { dialog ->
        when (dialog) {
            is DetailDialog.AddMyList -> {
                DialogCenterV1(
                    state = DialogStateV1(
                        type = DialogType.SUCCESS,
                        title = stringResource(R.string.success),
                        message = dialog.message,
                        primaryButtonText = OK_STRING
                    ),
                    onDismiss = { uiEvent.onDismissActiveDialog() }
                )
            }

            is DetailDialog.Error -> {
                DialogCenterV1(
                    state = DialogStateV1(
                        type = DialogType.ERROR,
                        title = ERROR_STRING,
                        message = dialog.message,
                        primaryButtonText = OK_STRING
                    ),
                    onDismiss = { uiEvent.onDismissActiveDialog() }
                )
            }

            is DetailDialog.Maintenance -> {
                DialogCenterV1(
                    state = DialogStateV1(
                        type = DialogType.WARNING,
                        title = WARNING_STRING,
                        message = dialog.message,
                        primaryButtonText = OK_STRING
                    ),
                    onDismiss = { uiEvent.onDismissActiveDialog() }
                )
            }
        }
    }

    DetailMovieContent(uiState, uiEvent, uiNavigation)
}

@Composable
fun DetailMovieContent(
    uiState: DetailStateListener,
    uiEvent: DetailEventListener,
    uiNavigation: DetailNavigationListener
) {
    when (uiState.detailState) {
        is Resource.Loading -> DetailMovieShimmer()
        is Resource.Success -> {
            val movie = uiState.detailState.data
            val video = (uiState.videosState as? Resource.Success)?.data

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
            ) {
                item { VideoHeader(movie.posterPath.orEmpty(), uiNavigation, video ?: MovieVideoResponse()) }
                item { MovieInfoSection(movie) }
                item { PlayDownloadSection { uiEvent.onPlayMovies() } }
                item { DescriptionSection(movie.overview) }
                item { ActionButtons(uiEvent, movie) }
            }
        }

        else -> {}
    }
}

@Composable
fun VideoHeader(
    posterPath: String,
    uiNavigation: DetailNavigationListener,
    video: MovieVideoResponse
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        AsyncImage(
            model = BuildConfig.TMDB_IMAGE_URL + posterPath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        IconButton(
            onClick = { uiNavigation.onNavigateToBack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.DarkGray) }

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.Center)
                .background(Color.LightGray, CircleShape)
                .padding(12.dp)
                .clickable { video.results.getOrNull(0)?.key?.let { uiNavigation.onNavigateToPlayMovie(it) } }
        )
    }
}

@Composable
fun MovieInfoSection(movie: MovieDetailResponse) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(movie.title, color = Color.DarkGray, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(movie.releaseDate.formatDateAutoLegacy("yyyy"), color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (movie.adult) "Adult" else "Child", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(movie.voteAverage.toString(), color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun PlayDownloadSection(onPlay: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Button(
            onClick = onPlay,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Icon(Icons.Default.PlayArrow, null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Play", color = Color.Black)
        }
    }
}

@Composable
fun DescriptionSection(description: String) {
    Text(description, color = Color.DarkGray, fontSize = 14.sp, modifier = Modifier.padding(16.dp))
}

@Composable
fun ActionButtons(uiEvent: DetailEventListener, movie: MovieDetailResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionItem(Icons.Default.Add, "My List") { uiEvent.onAddToMyList(movie) }
        ActionItem(Icons.Default.ThumbUp, "Rate") { uiEvent.onAddToMyLike(movie) }
        ActionItem(Icons.Default.Share, "Share") { uiEvent.onShareMovie(movie) }
    }
}

@Composable
fun ActionItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Icon(icon, contentDescription = null, tint = Color.DarkGray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text, color = Color.DarkGray, fontSize = 12.sp)
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun SeriesDetailScreenPreview() {
    MaterialTheme {
        DetailMovieContent(
            uiState = DetailStateListener(
                detailState = Resource.Success(
                    previewMovieDetail
                )
            ),
            uiNavigation = DetailNavigationListener({}, {}),
            uiEvent = DetailEventListener({}, {}, {}, {}, {}, {}, {})
        )
    }
}