/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: EditProfileScreen.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.53
 */

package com.mtv.app.movie.feature.ui.profile.edit

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mtv.app.movie.common.Constant.Title.EMAIL
import com.mtv.app.movie.common.Constant.Title.ENTER_YOUR_EMAIL
import com.mtv.app.movie.common.Constant.Title.ENTER_YOUR_NAME
import com.mtv.app.movie.common.Constant.Title.ENTER_YOUR_PASSWORD
import com.mtv.app.movie.common.Constant.Title.ENTER_YOUR_PHONE
import com.mtv.app.movie.common.Constant.Title.FULL_NAME
import com.mtv.app.movie.common.Constant.Title.PASSWORD
import com.mtv.app.movie.common.Constant.Title.PHONE
import com.mtv.app.movie.common.Constant.Title.SAVE_CHANGES
import com.mtv.app.movie.common.Constant.Title.UPDATE_PROFILE
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.base64ToBitmap
import com.mtv.app.movie.common.ui.BaseTextInput
import com.mtv.app.movie.common.ui.BaseToolbar
import com.mtv.app.movie.common.ui.BasePrimaryButton
import com.mtv.app.movie.common.uriToBase64
import com.mtv.app.movie.feature.event.profile.EditProfileDataListener
import com.mtv.app.movie.feature.event.profile.EditProfileDialog
import com.mtv.app.movie.feature.event.profile.EditProfileEventListener
import com.mtv.app.movie.feature.event.profile.EditProfileNavigationListener
import com.mtv.app.movie.feature.event.profile.EditProfileStateListener
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING

@Composable
fun EditProfileScreen(
    uiState: EditProfileStateListener,
    uiData: EditProfileDataListener,
    uiEvent: EditProfileEventListener,
    uiNavigation: EditProfileNavigationListener
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val scrollState = rememberScrollState()

    val name = rememberSaveable { mutableStateOf(EMPTY_STRING) }
    val phone = rememberSaveable { mutableStateOf(EMPTY_STRING) }
    val password = rememberSaveable { mutableStateOf(EMPTY_STRING) }

    val isFormValid = remember {
        derivedStateOf {
            name.value.isNotBlank() && phone.value.isNotBlank() && password.value.isNotBlank()
        }
    }

    remember { mutableStateOf(false) }

    var photoBase64 by remember { mutableStateOf(EMPTY_STRING) }
    var showPreview by remember { mutableStateOf(false) }
    val avatarBitmap = remember { mutableStateOf<Bitmap?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult

        photoBase64 = uriToBase64(
            context = context,
            uri = uri,
            maxSize = 512,
            quality = 70
        )
    }

    LaunchedEffect(uiData.userAccount) {
        uiData.userAccount?.let {
            name.value = it.name
            phone.value = it.phone
            photoBase64 = it.photo
        }
    }

    LaunchedEffect(photoBase64) {
        if (!isPreview && photoBase64.isNotBlank()) {
            avatarBitmap.value = base64ToBitmap(photoBase64)
        }
    }

    uiState.activeDialog?.let { dialog ->
        when (dialog) {
            is EditProfileDialog.Success -> {
                DialogCenterV1(
                    state = DialogStateV1(
                        type = DialogType.SUCCESS,
                        title = stringResource(R.string.success),
                        message = stringResource(R.string.successfully_update_profile),
                        primaryButtonText = OK_STRING
                    ),
                    onDismiss = {
                        uiEvent.onDismissActiveDialog()
                        uiNavigation.onBack()
                    }
                )
            }
        }
    }

    if (showPreview) {
        Dialog(onDismissRequest = { showPreview = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable { showPreview = false },
                contentAlignment = Alignment.Center
            ) {
                AvatarImage(
                    bitmap = avatarBitmap.value,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        BaseToolbar(
            title = UPDATE_PROFILE,
            onLeftClick = { uiNavigation.onBack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.White, CircleShape)
                        .clickable { showPreview = true },
                    contentAlignment = Alignment.Center
                ) {
                    AvatarImage(
                        bitmap = avatarBitmap.value,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                IconButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color(0xFF5C6BC0)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            BaseTextInput(
                label = FULL_NAME,
                value = name.value,
                onValueChange = { name.value = it },
                placeholder = ENTER_YOUR_NAME,
                leadingIcon = Icons.Default.Person
            )

            Spacer(Modifier.height(16.dp))


            BaseTextInput(
                label = EMAIL,
                value = uiData.userAccount?.email.orEmpty(),
                enabled = false,
                onValueChange = { },
                placeholder = ENTER_YOUR_EMAIL,
                leadingIcon = Icons.Default.Email
            )

            Spacer(Modifier.height(16.dp))

            BaseTextInput(
                label = PHONE,
                value = phone.value,
                onValueChange = { phone.value = it },
                placeholder = ENTER_YOUR_PHONE,
                leadingIcon = Icons.Default.Phone
            )

            Spacer(Modifier.height(16.dp))

            BaseTextInput(
                label = PASSWORD,
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = ENTER_YOUR_PASSWORD,
                leadingIcon = Icons.Default.Lock,
                isPassword = true
            )

            Spacer(Modifier.height(32.dp))

            BasePrimaryButton(
                text = SAVE_CHANGES,
                enabled = isFormValid.value,
                onClick = {
                    uiEvent.onSaveClicked(
                        name.value,
                        phone.value,
                        photoBase64,
                        uiData.userAccount?.email.orEmpty(),
                        password.value
                    )
                },
            )
        }
    }
}

@Composable
private fun AvatarImage(
    bitmap: Bitmap?,
    modifier: Modifier,
    contentScale: ContentScale
) {
    val imageModifier = modifier
        .clip(CircleShape)
        .background(Color.LightGray)

    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = imageModifier,
            contentScale = contentScale
        )
    } else {
        Image(
            painter = painterResource(R.drawable.ic_avatar),
            contentDescription = null,
            modifier = imageModifier,
            contentScale = contentScale
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen(
            uiState = EditProfileStateListener(),
            uiData = EditProfileDataListener(),
            uiEvent = EditProfileEventListener(
                onSaveClicked = { _, _, _, _, _ -> },
                onDismissActiveDialog = {}
            ),
            uiNavigation = EditProfileNavigationListener {},
        )
    }
}