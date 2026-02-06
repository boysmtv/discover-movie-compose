/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: EditProfileScreen.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.53
 */

package com.mtv.app.movie.feature.ui.profile.edit

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mtv.app.movie.common.Constant.Title.CURRENT_PASSWORD
import com.mtv.app.movie.common.Constant.Title.EDIT_PROFILE
import com.mtv.app.movie.common.Constant.Title.EMAIL
import com.mtv.app.movie.common.Constant.Title.FULL_NAME
import com.mtv.app.movie.common.Constant.Title.PHONE
import com.mtv.app.movie.common.Constant.Title.SAVE_CHANGES
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.base64ToBitmap
import com.mtv.app.movie.common.uriToBase64
import com.mtv.app.movie.feature.event.profile.edit.EditProfileDataListener
import com.mtv.app.movie.feature.event.profile.edit.EditProfileEventListener
import com.mtv.app.movie.feature.event.profile.edit.EditProfileNavigationListener
import com.mtv.app.movie.feature.event.profile.edit.EditProfileStateListener
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_3
)
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen(
            uiState = EditProfileStateListener(),
            uiData = EditProfileDataListener(),
            uiEvent = EditProfileEventListener(
                onSaveClicked = { _, _, _, _ -> }
            ),
            uiNavigation = EditProfileNavigationListener {},
        )
    }
}

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
    val currentPassword = rememberSaveable { mutableStateOf(EMPTY_STRING) }

    val currentPasswordVisible = remember { mutableStateOf(false) }

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

    if (uiState.updateState is ResourceFirebase.Success) {
        DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.SUCCESS,
                title = stringResource(R.string.success),
                message = stringResource(R.string.successfully_update_profile),
                primaryButtonText = OK_STRING
            ),
            onDismiss = {
                uiNavigation.onBack()
            }
        )
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2C225A),
                        Color(0xFF3B2AAE),
                        Color(0xFF5A3FD1)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = EDIT_PROFILE,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(40.dp))

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

            Spacer(Modifier.height(64.dp))

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                leadingIcon = {
                    Icon(Icons.Default.Person, null, Modifier.padding(start = 12.dp))
                },
                placeholder = { Text(FULL_NAME) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiData.userAccount?.email.orEmpty(),
                onValueChange = {},
                enabled = false,
                leadingIcon = {
                    Icon(Icons.Default.Email, null, Modifier.padding(start = 12.dp))
                },
                placeholder = { Text(EMAIL) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledContainerColor = Color.White,
                    disabledBorderColor = Color.Transparent,
                    disabledTextColor = Color.Gray,
                    disabledLeadingIconColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                leadingIcon = {
                    Icon(Icons.Default.Phone, null, Modifier.padding(start = 12.dp))
                },
                placeholder = { Text(PHONE) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = currentPassword.value,
                onValueChange = { currentPassword.value = it },
                leadingIcon = {
                    Icon(Icons.Default.Lock, null, Modifier.padding(start = 12.dp))
                },
                placeholder = { Text(CURRENT_PASSWORD) },
                trailingIcon = {
                    Icon(
                        if (currentPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        null,
                        Modifier
                            .clickable {
                                currentPasswordVisible.value = !currentPasswordVisible.value
                            }
                            .padding(end = 12.dp)
                    )
                },
                visualTransformation = if (currentPasswordVisible.value)
                    VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    uiEvent.onSaveClicked(
                        name.value,
                        phone.value,
                        photoBase64,
                        currentPassword.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5C6BC0)
                )
            ) {
                Text(SAVE_CHANGES, fontSize = 16.sp)
            }
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