package com.mtv.app.movie.feature.event.liked

import com.mtv.app.movie.data.model.movie.MovieDetailResponse

data class LikedEventListener(
    val onLoadLikedMovies: (movie: MovieDetailResponse) -> Unit,
    val onDeletedMovie: (movieId: String) -> Unit,
    val onDeletedAllMovies: () -> Unit,
)