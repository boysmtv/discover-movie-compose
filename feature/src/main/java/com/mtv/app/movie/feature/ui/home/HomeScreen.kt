package com.mtv.app.movie.feature.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.app.movie.common.R
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

val mockMoviesResponse = MoviesResponse(
    page = 1,
    totalPages = 55,
    totalResults = 1099,
    results = listOf(
        MoviesItemResponse(
            adult = false,
            backdropPath = "/sK3z0Naed3H1Wuh7a21YRVMxYqt.jpg",
            genreIds = listOf(9648, 53),
            id = 1368166,
            originalLanguage = "en",
            originalTitle = "The Housemaid",
            overview = "Trying to escape her past, Millie Calloway accepts a job as a live-in housemaid...",
            popularity = 167.0359,
            posterPath = "/cWsBscZzwu5brg9YjNkGewRUvJX.jpg",
            releaseDate = "2025-12-18",
            title = "The Housemaid",
            video = false,
            voteAverage = 7.232,
            voteCount = 321
        ),
        MoviesItemResponse(
            adult = false,
            backdropPath = "/mOeBBD49M72vCEXzgA1dS2MwGno.jpg",
            genreIds = listOf(27, 53),
            id = 1228246,
            originalLanguage = "en",
            originalTitle = "Five Nights at Freddy's 2",
            overview = "One year since the supernatural nightmare at Freddy Fazbear's Pizza...",
            popularity = 125.4234,
            posterPath = "/udAxQEORq2I5wxI97N2TEqdhzBE.jpg",
            releaseDate = "2025-12-03",
            title = "Five Nights at Freddy's 2",
            video = false,
            voteAverage = 6.8,
            voteCount = 571
        ),
        MoviesItemResponse(
            adult = false,
            backdropPath = "/tn95fumbkVAgXjOuw9CQOg0aYQz.jpg",
            genreIds = listOf(28, 80, 53),
            id = 1419406,
            originalLanguage = "zh",
            originalTitle = "捕风追影",
            overview = "Macau Police brings the tracking expert police officer out of retirement...",
            popularity = 103.6128,
            posterPath = "/e0RU6KpdnrqFxDKlI3NOqN8nHL6.jpg",
            releaseDate = "2025-08-16",
            title = "The Shadow's Edge",
            video = false,
            voteAverage = 6.911,
            voteCount = 241
        )
    )
)


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        checkState = ResourceFirebase.Success(CheckResponse("", "", "", "")),
        logoutState = ResourceFirebase.Success(LogoutResponse("")),
        nowPlayingState = Resource.Success(mockMoviesResponse),
        popularState = Resource.Success(mockMoviesResponse),
        topRatedState = Resource.Success(mockMoviesResponse),
        upComingState = Resource.Success(mockMoviesResponse),
        onDoCheck = {},
        onDoLogout = {},
        onDoGetNowPlaying = {},
        onSuccessLogout = {}
    )
}

@Composable
fun HomeScreen(
    checkState: ResourceFirebase<CheckResponse>,
    logoutState: ResourceFirebase<LogoutResponse>,
    nowPlayingState: Resource<MoviesResponse>,
    popularState: Resource<MoviesResponse>,
    topRatedState: Resource<MoviesResponse>,
    upComingState: Resource<MoviesResponse>,
    onDoCheck: (email: String) -> Unit,
    onDoLogout: (email: String) -> Unit,
    onDoGetNowPlaying: () -> Unit,
    onSuccessLogout: () -> Unit,
) {
    if (!LocalInspectionMode.current) {
        LaunchedEffect(Unit) {
            onDoCheck("Boys.mtv@gmail.com")
            onDoGetNowPlaying()
        }

        LaunchedEffect(logoutState) {
            if (logoutState is ResourceFirebase.Success) {
                onSuccessLogout()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HomeHeader(
            userName = "William B.",
            profileImage = R.drawable.ic_avatar,
            onNotificationClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.width(12.dp))

        if (nowPlayingState is Resource.Success) {
            HomeFeaturedSection(nowPlayingState.data.results)
            HomeFeaturedSection(nowPlayingState.data.results)
            HomeFeaturedSection(nowPlayingState.data.results)
        }

    }
}