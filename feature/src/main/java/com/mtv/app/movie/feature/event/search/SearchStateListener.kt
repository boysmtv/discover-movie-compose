package com.mtv.app.movie.feature.event.search

import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.based.core.network.utils.Resource

data class SearchStateListener(
    val searchState: Resource<MoviesResponse> = Resource.Loading,
    val upComingState: Resource<MoviesResponse> = Resource.Loading
)
