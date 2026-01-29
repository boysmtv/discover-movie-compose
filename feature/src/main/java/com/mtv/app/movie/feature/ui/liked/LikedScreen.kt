/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedScreen.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 12.10
 */

package com.mtv.app.movie.feature.ui.liked

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.feature.event.liked.LikedDataListener
import com.mtv.app.movie.feature.event.liked.LikedEventListener
import com.mtv.app.movie.feature.event.liked.LikedStateListener
import com.mtv.app.movie.feature.ui.home.mockMoviesResponse

@Composable
fun LikedScreen(
    uiState: LikedStateListener,
    uiData: LikedDataListener,
    uiEvent: LikedEventListener,
    uiNavigation: LikedEventListener
) {
    val scrollState = rememberScrollState()

    if (!LocalInspectionMode.current) {
        LaunchedEffect(Unit) {
            uiEvent.onLoadLikedMovies()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(scrollState)
    ) {
        if (uiData.movieLikedList.isNotEmpty()) {
            LikedFeaturedSection(
                movies = uiData.movieLikedList
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeFeaturedSection() {
    LikedFeaturedSection(MovieDetailResponse())
}

@Composable
fun LikedFeaturedSection(
    movies: List<MovieDetailResponse>,
    onClickedMovies: (MoviesItemResponse) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies.size) { index ->
                LikedFeatureMovieCard(
                    movie = movies[index],
                    onClick = { onClickedMovies(it) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeFeaturedMovieCard() {
    LikedFeatureMovieCard(
        movie = mockMoviesResponse.results[0],
        onClick = { }
    )
}

@Composable
fun LikedFeatureMovieCard(
    movie: MovieDetailResponse,
    onClick: (MoviesItemResponse) -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick(movie)
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(BuildConfig.TMDB_IMAGE_URL + movie.posterPath),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(6.dp)
                .background(Color.Red, RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = "Recently added",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}