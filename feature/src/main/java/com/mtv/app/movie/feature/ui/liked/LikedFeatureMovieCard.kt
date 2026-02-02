/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedFeatureMovieCard.kt
 *
 * Last modified by Dedy Wijaya on 30/01/26 09.09
 */

package com.mtv.app.movie.feature.ui.liked

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mtv.app.movie.common.BuildConfig
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.feature.utils.mockMovieDetailResponseList

@Preview(showBackground = true)
@Composable
fun PreviewHomeFeaturedMovieCard() {
    LikedFeatureMovieCard(
        movie = mockMovieDetailResponseList[0],
        onClickDetail = { },
        onClickDeleted = { },
    )
}

@Composable
fun LikedFeatureMovieCard(
    movie: MovieDetailResponse,
    onClickDetail: (MovieDetailResponse) -> Unit,
    onClickDeleted: (MovieDetailResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(120.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClickDetail(movie)
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                BuildConfig.TMDB_IMAGE_URL + movie.posterPath
            ),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 6.dp, bottom = 6.dp)
                .size(32.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = CircleShape
                )
                .clickable { onClickDeleted(movie) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "Delete Movie",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }

    }
}