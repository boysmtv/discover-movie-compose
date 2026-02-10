package com.mtv.app.movie.feature.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.feature.utils.mockMoviesResponse

@Composable
fun HomeFeaturedMovieCard(
    movie: MoviesItemResponse,
    onClick: (MoviesItemResponse) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .width(120.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = if (isPressed) 1.dp else 0.5.dp,
                color = if (isPressed)
                    Color.DarkGray.copy(alpha = 0.7f)
                else
                    Color.DarkGray.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
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

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun PreviewHomeFeaturedMovieCard() {
    HomeFeaturedMovieCard(
        movie = mockMoviesResponse.results[0],
        onClick = { }
    )
}
