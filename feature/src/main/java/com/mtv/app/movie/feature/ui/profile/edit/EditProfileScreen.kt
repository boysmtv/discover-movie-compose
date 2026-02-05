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
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockPerson
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.common.Constant.Title.CONFIRM_NEW_PASSWORD
import com.mtv.app.movie.common.Constant.Title.CURRENT_PASSWORD
import com.mtv.app.movie.common.Constant.Title.EDIT_PROFILE
import com.mtv.app.movie.common.Constant.Title.EMAIL
import com.mtv.app.movie.common.Constant.Title.FULL_NAME
import com.mtv.app.movie.common.Constant.Title.NEW_PASSWORD
import com.mtv.app.movie.common.Constant.Title.PHONE
import com.mtv.app.movie.common.Constant.Title.SAVE_CHANGES
import com.mtv.app.movie.common.R
import com.mtv.app.movie.feature.event.profile.edit.EditProfileEventListener
import com.mtv.app.movie.feature.event.profile.edit.EditProfileNavigationListener
import com.mtv.app.movie.feature.event.profile.edit.EditProfileStateListener
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
)
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen(
            uiState = EditProfileStateListener(),
            uiEvent = EditProfileEventListener(
                onSaveClicked = { _, _, _ -> },
                onPhotoSelected = {}
            ),
            uiNavigation = EditProfileNavigationListener {}
        )
    }
}

private fun uriToBase64(context: android.content.Context, uri: Uri): String {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val output = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output)
    return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT)
}

private fun base64ToBitmap(base64: String): Bitmap {
    val bytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

@Composable
fun EditProfileScreen(
    uiState: EditProfileStateListener,
    uiEvent: EditProfileEventListener,
    uiNavigation: EditProfileNavigationListener
) {
    val context = LocalContext.current

    val name = remember { mutableStateOf(EMPTY_STRING) }
    val email = remember { mutableStateOf(EMPTY_STRING) }
    val phone = remember { mutableStateOf(EMPTY_STRING) }

    val currentPassword = remember { mutableStateOf(EMPTY_STRING) }
    val newPassword = remember { mutableStateOf(EMPTY_STRING) }
    val newPasswordConfirm = remember { mutableStateOf(EMPTY_STRING) }

    val currentPasswordVisible = remember { mutableStateOf(false) }
    val newPasswordVisible = remember { mutableStateOf(false) }
    val newPasswordConfirmVisible = remember { mutableStateOf(false) }

    val photoBase64 = remember { mutableStateOf<String?>(null) }
    val showPhotoPreview = remember { mutableStateOf(false) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                photoBase64.value = uriToBase64(context, it)
                uiEvent.onPhotoSelected(it)
            }
        }

    val avatarBitmap = remember(photoBase64.value) {
        photoBase64.value?.let { base64ToBitmap(it) }
    }

    LaunchedEffect(Unit) {
        name.value = Constant.TestData.TESTDATA_NAME
        email.value = Constant.TestData.TESTDATA_EMAIL
        phone.value = Constant.TestData.TESTDATA_PHONE
    }

    if (uiState.updateState is ResourceFirebase.Success) {
        DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.SUCCESS,
                title = stringResource(R.string.success),
                message = stringResource(R.string.successfully_update_profile),
                primaryButtonText = OK_STRING
            ),
            onDismiss = uiNavigation.onBack
        )
    }
    /* ---------------- FULL IMAGE PREVIEW ---------------- */
    if (showPhotoPreview.value) {
        Dialog(onDismissRequest = { showPhotoPreview.value = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable { showPhotoPreview.value = false },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_avatar),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFB39DDB), Color(0xFF7986CB))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = EDIT_PROFILE,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            /* ---------------- PROFILE PHOTO ---------------- */
            Box(
                modifier = Modifier.size(110.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .background(Color.White, CircleShape)
                        .clickable { showPhotoPreview.value = true },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_avatar),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                IconButton(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Photo",
                        tint = Color(0xFF5C6BC0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            /* ---------------- FORM (UNCHANGED) ---------------- */
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
                value = email.value,
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

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = newPassword.value,
                onValueChange = { newPassword.value = it },
                leadingIcon = {
                    Icon(Icons.Default.Lock, null, Modifier.padding(start = 12.dp))
                },
                placeholder = { Text(NEW_PASSWORD) },
                trailingIcon = {
                    Icon(
                        if (newPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        null,
                        Modifier
                            .clickable {
                                newPasswordVisible.value = !newPasswordVisible.value
                            }
                            .padding(end = 12.dp)
                    )
                },
                visualTransformation = if (newPasswordVisible.value)
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

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = newPasswordConfirm.value,
                onValueChange = { newPasswordConfirm.value = it },
                leadingIcon = {
                    Icon(Icons.Default.LockPerson, null, Modifier.padding(start = 12.dp))
                },
                placeholder = { Text(CONFIRM_NEW_PASSWORD) },
                trailingIcon = {
                    Icon(
                        if (newPasswordConfirmVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        null,
                        Modifier
                            .clickable {
                                newPasswordConfirmVisible.value = !newPasswordConfirmVisible.value
                            }
                            .padding(end = 12.dp)
                    )
                },
                visualTransformation = if (newPasswordConfirmVisible.value)
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
                        photoBase64.value
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
