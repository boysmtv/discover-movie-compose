package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.toMap
import com.mtv.app.movie.data.model.movie.MoviesResponse
import com.mtv.app.movie.data.model.request.CheckRequest
import com.mtv.app.movie.data.model.request.LogoutRequest
import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.app.movie.domain.usecase.movie.MoviesNowPlayingUseCase
import com.mtv.app.movie.domain.usecase.movie.MoviesPopularUseCase
import com.mtv.app.movie.domain.usecase.movie.MoviesTopRatedUseCase
import com.mtv.app.movie.domain.usecase.movie.MoviesUpComingUseCase
import com.mtv.app.movie.domain.user.CheckUseCase
import com.mtv.app.movie.domain.user.LogoutUseCase
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val checkUseCase: CheckUseCase<CheckResponse>,
    private val logoutUseCase: LogoutUseCase<LogoutResponse>,
    private val getNowPlayingUseCase: MoviesNowPlayingUseCase,
    private val getPopularUseCase: MoviesPopularUseCase,
    private val getTopRatedUseCase: MoviesTopRatedUseCase,
    private val getUpComingUseCase: MoviesUpComingUseCase
) : BaseViewModel() {

    val checkState = MutableStateFlow<ResourceFirebase<CheckResponse>>(ResourceFirebase.Loading)
    val logoutState = MutableStateFlow<ResourceFirebase<LogoutResponse>>(ResourceFirebase.Loading)
    val nowPlayingState = MutableStateFlow<Resource<MoviesResponse>>(Resource.Loading)
    val popularState = MutableStateFlow<Resource<MoviesResponse>>(Resource.Loading)
    val topRatedState = MutableStateFlow<Resource<MoviesResponse>>(Resource.Loading)
    val upComingState = MutableStateFlow<Resource<MoviesResponse>>(Resource.Loading)

    fun doCheck(email: String) = launchFirebaseUseCase(checkState) {
        checkUseCase(
            param = CheckRequest(
                email = email
            ).toMap()
        )
    }

    fun doLogout(email: String) = launchFirebaseUseCase(logoutState) {
        logoutUseCase(
            param = LogoutRequest(
                email = email
            ).toMap()
        )
    }

    fun getNowPlayingUseCase() =
        launchUseCase(nowPlayingState) { getNowPlayingUseCase(param = Unit) }

    fun getPopularMovies() = launchUseCase(popularState) { getPopularUseCase(param = Unit) }

    fun getTopRatedUseCase() = launchUseCase(topRatedState) { getTopRatedUseCase(param = Unit) }

    fun getUpComingUseCase() = launchUseCase(upComingState) { getUpComingUseCase(param = Unit) }

}