/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedFeatureMovieCard.kt
 *
 * Last modified by Dedy Wijaya on 30/01/26 09.09
 */

package com.mtv.app.movie.feature.ui.liked

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        onClick = { }
    )
}

@Composable
fun LikedFeatureMovieCard(
    movie: MovieDetailResponse,
    onClick: (MovieDetailResponse) -> Unit
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