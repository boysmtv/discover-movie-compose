package com.mtv.app.movie.feature.event.profile

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import androidx.core.graphics.createBitmap

data class ProfileDataListener(
    val userAccount: LoginResponse? = null,
    val userDummy: ProfileDummyData = ProfileDummyData()
)

@Immutable
data class ProfileDummyData(
    val name: String = "Sana Afzal",
    val email: String = "sanaafzal291@gmail.com",
    val photo: Bitmap? = null
)