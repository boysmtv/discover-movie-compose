package com.mtv.app.movie.feature.event.detail

import com.mtv.app.movie.data.model.movie.MovieDetailResponse

data class DetailEventListener(
    val onLoadMovies: () -> Unit,
    val onPlayMovies: () -> Unit,
    val onConsumePlayEvent: () -> Unit,
    val onAddToMyList: (movie: MovieDetailResponse) -> Unit,
    val onAddToMyLike: (movie: MovieDetailResponse) -> Unit,
    val onShareMovie: (movie: MovieDetailResponse) -> Unit,
    val onDismissAddMyList: () -> Unit,
    val onDismissAddMyLike: () -> Unit,
)