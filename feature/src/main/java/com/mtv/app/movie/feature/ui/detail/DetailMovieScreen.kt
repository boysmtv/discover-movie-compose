package com.mtv.app.movie.feature.ui.detail

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.common.formatDateAutoLegacy
import com.mtv.app.movie.data.model.movie.GenreItem
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.feature.event.detail.DetailEventListener
import com.mtv.app.movie.feature.event.detail.DetailNavigationListener
import com.mtv.app.movie.feature.event.detail.DetailStateListener
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING

private val previewMovieDetail = MovieDetailResponse(
    adult = false,
    backdropPath = "/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg",
    belongsToCollection = null,
    budget = 100_000_000,
    genres = listOf(
        GenreItem(1, "Drama"),
        GenreItem(2, "Action")
    ),
    homepage = "https://example.com",
    id = 1,
    imdbId = "tt1234567",
    originalLanguage = "en",
    originalTitle = "The Last of Us",
    overview = "Twenty years after modern civilization has been destroyed...",
    popularity = 999.9,
    posterPath = "/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",
    productionCompanies = emptyList(),
    productionCountries = emptyList(),
    releaseDate = "2023-01-15",
    revenue = 500_000_000,
    runtime = 120,
    spokenLanguages = emptyList(),
    status = "Released",
    tagline = "Hope survives.",
    title = "The Last of Us",
    video = false,
    voteAverage = 8.9,
    voteCount = 12345
)

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
            uiState = DetailStateListener(detailState = Resource.Success(previewMovieDetail)),
            uiNavigation = DetailNavigationListener(
                onNavigateToBack = {},
                onNavigateToPlayMovie = {}
            ),
            uiEvent = DetailEventListener(
                onLoadMovies = {},
                onPlayMovies = {},
                onConsumePlayEvent = {}
            )
        )
    }
}

@Composable
fun DetailMovieScreen(
    uiState: DetailStateListener,
    uiEvent: DetailEventListener,
    uiNavigation: DetailNavigationListener,
) {
    LaunchedEffect(Unit) {
        uiEvent.onLoadMovies()
    }

    LaunchedEffect(uiState.videosState) {
        val state = uiState.videosState
        if (state is Resource.Success) {
            val key = state.data.results.firstOrNull()?.key
            if (!key.isNullOrEmpty()) {
                uiNavigation.onNavigateToPlayMovie(key)
                uiEvent.onConsumePlayEvent()
            }
        }
    }

    DetailMovieContent(
        uiState = uiState,
        uiEvent = uiEvent,
        uiNavigation = uiNavigation
    )
}

@Composable
fun DetailMovieContent(
    uiState: DetailStateListener,
    uiEvent: DetailEventListener,
    uiNavigation: DetailNavigationListener
) {
    val movie = (uiState.detailState as? Resource.Success)?.data
    val video = (uiState.videosState as? Resource.Success)?.data

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        item {
            VideoHeader(
                posterUrl = (BuildConfig.TMDB_IMAGE_URL + movie?.posterPath),
                onBack = uiNavigation.onNavigateToBack,
                onPlay = {
                    uiNavigation.onNavigateToPlayMovie(video?.results[0]?.key ?: EMPTY_STRING)
                },
            )
        }

        item {
            MovieInfoSection(
                title = movie?.title.orEmpty(),
                year = movie?.releaseDate?.formatDateAutoLegacy("yyyy").orEmpty(),
                age = if (movie?.adult == true) "Adult" else "Child",
                rating = movie?.voteAverage?.toString().orEmpty()
            )
        }

        item {
            PlayDownloadSection(
                onPlay = uiEvent.onPlayMovies,
            )
        }

        item {
            DescriptionSection(movie?.overview.orEmpty())
        }

        item {
            ActionButtons()
        }

        item {
            Text(
                "More Like This",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        item {
            MoreLikeThisSection(emptyList())
        }
    }
}

@Composable
fun VideoHeader(
    posterUrl: String,
    onBack: () -> Unit,
    onPlay: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
        )

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.Center)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .padding(12.dp)
                .clickable { onPlay() }
        )

    }
}


@Composable
fun MovieInfoSection(
    title: String,
    year: String,
    age: String,
    rating: String
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(year, color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(age, color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(rating, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun PlayDownloadSection(
    onPlay: () -> Unit,
) {
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
    Text(
        text = description,
        color = Color.White,
        fontSize = 14.sp,
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionItem(Icons.Default.Add, "My List")
        ActionItem(Icons.Default.ThumbUp, "Rate")
        ActionItem(Icons.Default.Share, "Share")
    }
}

@Composable
fun ActionItem(icon: ImageVector, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, tint = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun MoreLikeThisSection(movies: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movies) { poster ->
            AsyncImage(
                model = poster,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(4.dp)
                    .height(180.dp)
            )
        }
    }
}
