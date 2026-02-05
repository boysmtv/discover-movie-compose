/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: EditProfileContract.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.58
 */

package com.mtv.app.movie.feature.event.profile.edit

import android.net.Uri
import com.mtv.based.core.network.utils.ResourceFirebase

data class EditProfileStateListener(
    val updateState: ResourceFirebase<Unit> = ResourceFirebase.Loading,
    val uploadPhotoState: ResourceFirebase<String> = ResourceFirebase.Loading
)

data class EditProfileEventListener(
    val onSaveClicked: (
        name: String,
        phone: String,
        photoUrl: String?
    ) -> Unit,
    val onPhotoSelected: (uri: Uri) -> Unit
)

data class EditProfileNavigationListener(
    val onBack: () -> Unit
)
