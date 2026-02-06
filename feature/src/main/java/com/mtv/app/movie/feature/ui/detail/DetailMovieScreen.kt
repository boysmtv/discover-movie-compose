package com.mtv.app.movie.feature.ui.detail

import android.content.res.Configuration
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
import androidx.compose.ui.graphics.Brush
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
import com.mtv.app.movie.common.StateMovieResult
import com.mtv.app.movie.common.formatDateAutoLegacy
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.data.model.movie.MovieVideoResponse
import com.mtv.app.movie.feature.event.detail.DetailEventListener
import com.mtv.app.movie.feature.event.detail.DetailNavigationListener
import com.mtv.app.movie.feature.event.detail.DetailStateListener
import com.mtv.app.movie.feature.utils.previewMovieDetail
import com.mtv.app.movie.feature.utils.previewVideoResponse
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.ERROR_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
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
            uiEvent = DetailEventListener({}, {}, {}, {}, {}, {}, {}, {})
        )
    }
}

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

    HandleAddState(uiState.addMyListState, "Movie added to your list") { uiEvent.onDismissAddMyList() }
    HandleAddState(uiState.addMyLikeState, "Movie added to your liked") { uiEvent.onDismissAddMyLike() }

    DetailMovieContent(uiState, uiEvent, uiNavigation)
}

@Composable
fun HandleAddState(state: StateMovieResult, successMessage: String, onDismiss: () -> Unit) {
    when (state) {
        is StateMovieResult.Success -> DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.SUCCESS,
                title = stringResource(R.string.success),
                message = successMessage,
                primaryButtonText = OK_STRING
            ),
            onDismiss = onDismiss
        )

        is StateMovieResult.Error -> DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.ERROR,
                title = ERROR_STRING,
                message = state.message,
                primaryButtonText = OK_STRING
            ),
            onDismiss = onDismiss
        )

        StateMovieResult.None -> {}
    }
}

@Composable
fun DetailMovieContent(
    uiState: DetailStateListener,
    uiEvent: DetailEventListener,
    uiNavigation: DetailNavigationListener
) {
    val movie = (uiState.detailState as? Resource.Success)?.data ?: previewMovieDetail
    val video = (uiState.videosState as? Resource.Success)?.data ?: previewVideoResponse

    LazyColumn(
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
        item { VideoHeader(movie.posterPath ?: EMPTY_STRING, uiNavigation, video) }
        item { MovieInfoSection(movie) }
        item { PlayDownloadSection { uiEvent.onPlayMovies() } }
        item { DescriptionSection(movie.overview) }
        item { ActionButtons(uiEvent, movie) }
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
        ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White) }

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.Center)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .padding(12.dp)
                .clickable { video.results.getOrNull(0)?.key?.let { uiNavigation.onNavigateToPlayMovie(it) } }
        )
    }
}

@Composable
fun MovieInfoSection(movie: MovieDetailResponse) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(movie.title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
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
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Icon(Icons.Default.PlayArrow, null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Play", color = Color.Black)
        }
    }
}

@Composable
fun DescriptionSection(description: String) {
    Text(description, color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(16.dp))
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
        Icon(icon, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}
