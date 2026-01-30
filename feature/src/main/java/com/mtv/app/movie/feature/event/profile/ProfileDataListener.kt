package com.mtv.app.movie.feature.event.profile

import androidx.compose.runtime.Immutable
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING

data class ProfileDataListener(
    val userAccount: LoginResponse? = null,
    val userDummy: ProfileDummyData = ProfileDummyData()
)


@Immutable
data class ProfileDummyData(
    val name: String = "Sana Afzal",
    val email: String = "sanaafzal291@gmail.com",
    val photoUrl: String = EMPTY_STRING
)