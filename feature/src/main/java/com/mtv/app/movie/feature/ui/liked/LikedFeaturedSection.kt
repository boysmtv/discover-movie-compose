/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedFeaturedSection.kt
 *
 * Last modified by Dedy Wijaya on 30/01/26 09.09
 */

package com.mtv.app.movie.feature.ui.liked

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.feature.utils.mockMovieDetailResponseList

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun PreviewHomeFeaturedSection() {
    LikedFeaturedSection(
        movies = mockMovieDetailResponseList,
        onClickDetail = { },
        onClickDeleted = { }
    )
}

@Composable
fun LikedFeaturedSection(
    movies: List<MovieDetailResponse>,
    onClickDetail: (Int) -> Unit,
    onClickDeleted: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFF5F5F5)),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            AnimatedVisibility(
                visible = true,
                exit = fadeOut() + scaleOut(),
                enter = fadeIn() + scaleIn()
            ) {
                LikedFeatureMovieCard(
                    movie = movie,
                    onClickDetail = { onClickDetail(movie.id) },
                    onClickDeleted = { onClickDeleted(movie.id) }
                )
            }
        }
    }
}
