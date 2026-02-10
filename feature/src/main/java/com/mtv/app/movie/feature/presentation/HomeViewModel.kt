/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: HomeViewModel.kt
 *
 * Last modified by Dedy Wijaya on 29/01/26 10.50
 */

package com.mtv.app.movie.feature.presentation

import com.mtv.app.core.provider.based.BaseViewModel
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.movie.common.ConstantPreferences
import com.mtv.app.movie.common.UiOwner
import com.mtv.app.movie.common.updateUiDataListener
import com.mtv.app.movie.common.valueFlowOf
import com.mtv.app.movie.data.model.request.LogoutRequest
import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.app.movie.domain.movie.MoviesNowPlayingUseCase
import com.mtv.app.movie.domain.movie.MoviesPopularUseCase
import com.mtv.app.movie.domain.movie.MoviesTopRatedUseCase
import com.mtv.app.movie.domain.movie.MoviesUpComingUseCase
import com.mtv.app.movie.domain.user.CheckUseCase
import com.mtv.app.movie.domain.user.LogoutUseCase
import com.mtv.app.movie.feature.contract.HomeDataListener
import com.mtv.app.movie.feature.contract.HomeStateListener
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
    private val getUpComingUseCase: MoviesUpComingUseCase,
    securePrefs: SecurePrefs
) : BaseViewModel(), UiOwner<HomeStateListener, HomeDataListener> {

    /** UI STATE : LOADING / ERROR / SUCCESS (API Response) */
    override val uiState = MutableStateFlow(HomeStateListener())

    /** UI DATA : DATA PERSIST (Prefs) */
    override val uiData = MutableStateFlow(HomeDataListener())

    init {
        val account = securePrefs.getObject(
            key = ConstantPreferences.USER_ACCOUNT,
            clazz = LoginResponse::class.java
        )
        updateUiDataListener(uiData) { copy(loginResponse = account) }
    }

    /** GET ALL MOVIES */
    fun getMovies() {
        getNowPlaying()
        getPopular()
        getTopRated()
        getUpComing()
    }

    /** GET NOW PLAYING MOVIES */
    fun getNowPlaying() = launchUseCase(
        loading = false,
        target = uiState.valueFlowOf(
            get = { it.nowPlayingState },
            set = { state -> copy(nowPlayingState = state) }
        ),
        block = { getNowPlayingUseCase(Unit) }
    )

    /** GET POPULAR MOVIES */
    fun getPopular() = launchUseCase(
        loading = false,
        target = uiState.valueFlowOf(
            get = { it.popularState },
            set = { state -> copy(popularState = state) }
        ),
        block = { getPopularUseCase(Unit) }
    )

    /** GET TOP RATED MOVIES */
    fun getTopRated() = launchUseCase(
        loading = false,
        target = uiState.valueFlowOf(
            get = { it.topRatedState },
            set = { state -> copy(topRatedState = state) }
        ),
        block = { getTopRatedUseCase(Unit) }
    )

    /** GET UP COMING MOVIES */
    fun getUpComing() = launchUseCase(
        loading = false,
        target = uiState.valueFlowOf(
            get = { it.upComingState },
            set = { state -> copy(upComingState = state) }
        ),
        block = { getUpComingUseCase(Unit) }
    )

    /** LOGOUT */
    fun doLogout(email: String) = launchFirebaseUseCase(
        target = uiState.valueFlowOf(
            get = { it.logoutState },
            set = { state -> copy(logoutState = state) }
        ),
        block = { logoutUseCase(LogoutRequest(email)) }
    )

}