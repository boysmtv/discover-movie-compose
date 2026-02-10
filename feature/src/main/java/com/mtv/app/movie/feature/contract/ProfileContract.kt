/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: ProfileContract.kt
 *
 * Last modified by Dedy Wijaya on 09/02/26 15.58
 */

package com.mtv.app.movie.feature.contract

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.common.Constant.Title.UNDER_MAINTENANCE
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase

data class ProfileStateListener(
    val onProfileState: ResourceFirebase<LoginResponse> = ResourceFirebase.Loading,
    val onLogoutState: Resource<Unit> = Resource.Loading,
    val onAddPinState: Resource<Unit> = Resource.Loading,
    val onSettingState: Resource<Unit> = Resource.Loading,
    val activeDialog: ProfileDialog? = null
)

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

data class ProfileEventListener(
    val onLogout: () -> Unit = {},
    val onNavigateToSettings: () -> Unit = {},
    val onDismissActiveDialog: () -> Unit = {},
)

data class ProfileNavigationListener(
    val onNavigateToEditProfile: () -> Unit = {},
    val onNavigateToChangePassword: () -> Unit = {},
    val navigateToLoginAndClearBackStack: () -> Unit = {}
)

sealed class ProfileDialog {
    data class Maintenance(
        val message: String = UNDER_MAINTENANCE
    ) : ProfileDialog()
}