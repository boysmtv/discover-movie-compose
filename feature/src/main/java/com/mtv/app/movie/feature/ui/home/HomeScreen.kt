package com.mtv.app.movie.feature.ui.home

import android.content.res.Configuration
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.common.MovieCategory
import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.app.movie.common.R
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.feature.event.home.HomeDataListener
import com.mtv.app.movie.feature.event.home.HomeEventListener
import com.mtv.app.movie.feature.event.home.HomeNavigationListener
import com.mtv.app.movie.feature.event.home.HomeStateListener
import com.mtv.app.movie.feature.utils.mockMoviesResponse
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
)
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
                        createdAt = "21/12/26"
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
                photo = "https://i.pinimg.com/736x/41/66/b0/4166b08e8d32aff9e00f2bee5e2dc4dd.jpg",
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
    val scrollState = rememberScrollState()

    if (!LocalInspectionMode.current) {
        LaunchedEffect(Unit) {
            uiEvent.onCheck(Constant.TestData.TESTDATA_EMAIL)
            uiEvent.onLoadMovies()
        }

        LaunchedEffect(uiState.logoutState) {
            if (uiState.logoutState is ResourceFirebase.Success) {
                uiNavigation.onNavigateToLogin()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF181818),
                        Color(0xFF0F0F0F),
                        Color(0xFF000000)
                    )
                )
            )
    ) {
        HomeHeader(
            userName = uiData.loginResponse?.name ?: EMPTY_STRING,
            profileImage = R.drawable.ic_avatar,
            onNotificationClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (uiState.upComingState is Resource.Success) {
                HomeFeaturedSection(
                    movieCategory = MovieCategory.UP_COMING,
                    movies = uiState.upComingState.data.results,
                    onClickedMovies = { uiNavigation.onNavigateToMovieDetail(it) }
                )
            }

            if (uiState.nowPlayingState is Resource.Success) {
                HomeFeaturedSection(
                    movieCategory = MovieCategory.NOW_PLAYING,
                    movies = uiState.nowPlayingState.data.results,
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

            if (uiState.popularState is Resource.Success) {
                HomeFeaturedSection(
                    movieCategory = MovieCategory.POPULAR,
                    movies = uiState.popularState.data.results,
                    onClickedMovies = { uiNavigation.onNavigateToMovieDetail(it) }
                )
            }
        }
    }
}