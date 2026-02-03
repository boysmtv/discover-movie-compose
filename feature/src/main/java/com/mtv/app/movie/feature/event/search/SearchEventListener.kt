package com.mtv.app.movie.feature.event.search

data class SearchEventListener(
    val onDoSearch: (String) -> Unit
)