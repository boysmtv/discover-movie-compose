/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedFeaturedSection.kt
 *
 * Last modified by Dedy Wijaya on 30/01/26 09.09
 */

package com.mtv.app.movie.feature.ui.liked

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.data.model.movie.MovieDetailResponse
import com.mtv.app.movie.feature.utils.mockMovieDetailResponseList

@Preview(showBackground = true)
@Composable
fun PreviewHomeFeaturedSection() {
    LikedFeaturedSection(
        movies = mockMovieDetailResponseList,
        onClickedMovies = {

        }
    )
}

@Composable
fun LikedFeaturedSection(
    movies: List<MovieDetailResponse>,
    onClickedMovies: (MovieDetailResponse) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        items(movies) { movie ->
            LikedFeatureMovieCard(
                movie = movie,
                onClick = { onClickedMovies(movie) }
            )
        }
    }
}