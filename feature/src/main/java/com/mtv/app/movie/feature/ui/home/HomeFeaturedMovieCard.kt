package com.mtv.app.movie.feature.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.common.R
import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.feature.utils.mockMoviesResponse

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
)
@Composable
fun PreviewHomeFeaturedMovieCard() {
    HomeFeaturedMovieCard(
        movie = mockMoviesResponse.results[0],
        onClick = { }
    )
}

@Composable
fun HomeFeaturedMovieCard(
    movie: MoviesItemResponse,
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
    }
}
