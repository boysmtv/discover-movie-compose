package com.mtv.app.movie.feature.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.MovieCategory
import com.mtv.app.movie.data.model.movie.MoviesItemResponse

@Preview(showBackground = true)
@Composable
fun PreviewHomeFeaturedSection() {
    HomeFeaturedSection(
        movieCategory = MovieCategory.NOW_PLAYING,
        movies = mockMoviesResponse.results
    )
}

@Composable
fun HomeFeaturedSection(
    movieCategory: MovieCategory,
    movies: List<MoviesItemResponse>
) {

    if (movies.isEmpty()) return

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = movieCategory.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies.size) { index ->
                HomeFeaturedMovieCard(movie = movies[index])
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
