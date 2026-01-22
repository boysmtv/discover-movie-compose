package com.mtv.app.movie.feature.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.common.MovieCategory
import com.mtv.app.movie.data.model.movie.MoviesItemResponse
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.app.movie.common.R
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.feature.event.home.HomeDataListener
import com.mtv.app.movie.feature.event.home.HomeEventListener
import com.mtv.app.movie.feature.event.home.HomeNavigationListener
import com.mtv.app.movie.feature.event.home.HomeStateListener
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING

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
        uiState = HomeStateListener(
            checkState = ResourceFirebase.Success(
                data = CheckResponse(
                    name = "Dedy Wijaya",
                    email = "Dedy.wijaya@ikonsultan.com",
                    phone = "08158844424",
                    createdAt = "21/12/26"
                )
            ),
            logoutState = ResourceFirebase.Success(
                data =
                    LogoutResponse(
                        date = "21/12/26"
                    )
            ),
            nowPlayingState = Resource.Success(mockMoviesResponse),
            popularState = Resource.Success(mockMoviesResponse),
            topRatedState = Resource.Success(mockMoviesResponse),
            upComingState = Resource.Success(mockMoviesResponse),
        ),
        uiData = HomeDataListener(
            loginResponse = LoginResponse(
                name = "Dedy Wijaya",
                email = "Dedy.wijaya@ikonsultan.com",
                phone = "08158844424",
                createdAt = "21/12/26"
            )
        ),
        uiEvent = HomeEventListener(
            onCheck = {},
            onLogout = {},
            onLoadMovies = {}
        ),
        uiNavigation = HomeNavigationListener(
            onNavigateToLogin = {},
            onNavigateToMovieDetail = {},
        )
    )
}

@Composable
fun HomeScreen(
    uiState: HomeStateListener,
    uiData: HomeDataListener,
    uiEvent: HomeEventListener,
    uiNavigation: HomeNavigationListener
) {
    if (!LocalInspectionMode.current) {
        LaunchedEffect(Unit) {
            uiEvent.onCheck(Constant.TestData.EMAIL)
            uiEvent.onLoadMovies()
        }

        LaunchedEffect(uiState.logoutState) {
            if (uiState.logoutState is ResourceFirebase.Success) {
                uiNavigation.onNavigateToLogin()
            }
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HomeHeader(
            userName = uiData.loginResponse?.name ?: EMPTY_STRING,
            profileImage = R.drawable.ic_avatar,
            onNotificationClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(scrollState)
        ) {
            if (uiState.nowPlayingState is Resource.Success) {
                HomeFeaturedSection(
                    movieCategory = MovieCategory.NOW_PLAYING,
                    movies = uiState.nowPlayingState.data.results,
                    onClickedMovies = { uiNavigation.onNavigateToMovieDetail(it) }
                )
            }

            if (uiState.popularState is Resource.Success) {
                HomeFeaturedSection(
                    movieCategory = MovieCategory.POPULAR,
                    movies = uiState.popularState.data.results,
                    onClickedMovies = { uiNavigation.onNavigateToMovieDetail(it) }
                )
            }

            if (uiState.topRatedState is Resource.Success) {
                HomeFeaturedSection(
                    movieCategory = MovieCategory.TOP_RATED,
                    movies = uiState.topRatedState.data.results,
                    onClickedMovies = { uiNavigation.onNavigateToMovieDetail(it) }
                )
            }

            if (uiState.upComingState is Resource.Success) {
                HomeFeaturedSection(
                    movieCategory = MovieCategory.UP_COMING,
                    movies = uiState.upComingState.data.results,
                    onClickedMovies = { uiNavigation.onNavigateToMovieDetail(it) }
                )
            }
        }
    }
}