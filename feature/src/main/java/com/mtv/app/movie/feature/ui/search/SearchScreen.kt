/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SearchScreen.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 10.51
 */

package com.mtv.app.movie.feature.ui.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mtv.app.movie.common.ui.RatingStars
import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.app.movie.feature.contract.SearchDataListener
import com.mtv.app.movie.feature.contract.SearchEventListener
import com.mtv.app.movie.feature.contract.SearchNavigationListener
import com.mtv.app.movie.feature.contract.SearchStateListener
import com.mtv.app.movie.feature.utils.MovieTagChipAnimated
import com.mtv.app.movie.feature.utils.mockSearchMovies
import com.mtv.app.movie.feature.utils.priorityTag
import com.mtv.based.core.network.utils.Resource
import kotlinx.coroutines.FlowPreview
import org.threeten.bp.LocalDate

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    uiState: SearchStateListener,
    uiData: SearchDataListener,
    uiEvent: SearchEventListener,
    uiNavigation: SearchNavigationListener
) {

    val isSearching = uiData.query.isNotBlank()

    val currentState = if (isSearching) {
        uiState.searchState
    } else {
        uiState.upComingState
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        NetflixSearchBar(
            query = uiData.query,
            onQueryChange = { newQuery -> uiEvent.onDoSearch(newQuery) },
            onClearClick = { uiEvent.onDoSearch("") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (currentState) {
            is Resource.Loading -> SearchShimmer()
            is Resource.Success -> {
                val movies = currentState.data.results

                if (movies.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFF5F5F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isSearching)
                                "No movies found"
                            else
                                "No upcoming movies",
                            color = Color.DarkGray.copy(alpha = 0.7f)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5))
                    ) {
                        items(movies) { movie ->
                            MovieItemNetflix(
                                movie = movie,
                                onClickMovie = {
                                    uiNavigation.onNavigateToMovieDetail(it)
                                }
                            )
                        }
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
fun NetflixSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(48.dp)
                .background(Color(0xFFFFFFFF), RoundedCornerShape(24.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.DarkGray.copy(alpha = 0.7f),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    cursorBrush = SolidColor(Color.LightGray),
                    textStyle = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (query.isEmpty()) {
                                Text(
                                    text = "Search movies...",
                                    color = Color.DarkGray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 8.dp, end = 12.dp)
                )

                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = onClearClick,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear text",
                            tint = Color.DarkGray.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun MovieItemNetflix(
    movie: MoviesItemResponse,
    onClickMovie: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .clickable { onClickMovie(movie.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            val posterUrl = movie.posterPath?.let {
                "https://image.tmdb.org/t/p/w200$it"
            }

            if (posterUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(posterUrl),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 0.5.dp,
                            color = Color.DarkGray.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp)
                        .background(Color.DarkGray, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Image", fontSize = 12.sp, color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    movie.title,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                val priorityTag = remember(movie.id) {
                    movie.priorityTag(
                        today = LocalDate.parse("2024-01-01")
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Text(
                        text = movie.releaseDate,
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    MovieTagChipAnimated(priorityTag)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    movie.overview,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingStars(rating = movie.voteAverage)

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = String.format("%.1f", movie.voteAverage),
                        color = Color(0xFFFFD700),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = Color.DarkGray.copy(alpha = 0.6f)
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun NetflixSearchBarPreview() {
    NetflixSearchBar(
        query = "",
        onQueryChange = {},
        onClearClick = {}
    )
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun MovieItemNetflixPreview() {
    MovieItemNetflix(
        movie = mockSearchMovies.first(),
        onClickMovie = {}
    )
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        uiState = SearchStateListener(upComingState = Resource.Success(MoviesResponse(results = mockSearchMovies))),
        uiData = SearchDataListener(query = ""),
        uiEvent = SearchEventListener(onDoSearch = {}),
        uiNavigation = SearchNavigationListener(onNavigateToMovieDetail = {})
    )
}