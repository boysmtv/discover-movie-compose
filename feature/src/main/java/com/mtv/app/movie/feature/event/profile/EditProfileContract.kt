/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: EditProfileContract.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.58
 */

package com.mtv.app.movie.feature.event.profile

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.based.core.network.utils.ResourceFirebase

data class EditProfileStateListener(
    val updateState: ResourceFirebase<Unit> = ResourceFirebase.Loading,
    val uploadPhotoState: ResourceFirebase<String> = ResourceFirebase.Loading
)

data class EditProfileDataListener(
    val userAccount: LoginResponse? = null,
    val userDummy: EditProfileDummyData = EditProfileDummyData()
)

@Immutable
data class EditProfileDummyData(
    val name: String = "Sana Afzal",
    val email: String = "sanaafzal291@gmail.com",
    val photo: Bitmap? = null
)

data class EditProfileEventListener(
    val onSaveClicked: (
        name: String,
        phone: String,
        photo: String,
        email: String,
        password: String
    ) -> Unit,
)

data class EditProfileNavigationListener(
    val onBack: () -> Unit
)
