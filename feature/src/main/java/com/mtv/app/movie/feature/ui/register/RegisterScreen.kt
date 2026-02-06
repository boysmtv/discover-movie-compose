package com.mtv.app.movie.feature.ui.register

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.LockPerson
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.uriToBase64
import com.mtv.app.movie.feature.event.register.RegisterEventListener
import com.mtv.app.movie.feature.event.register.RegisterNavigationListener
import com.mtv.app.movie.feature.event.register.RegisterStateListener
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.WARNING_STRING

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(
        uiState = RegisterStateListener(),
        uiEvent = RegisterEventListener(
            onRegisterByEmailClicked = { _, _, _, _, _ -> },
            onRegisterByGoogleClicked = {},
            onRegisterByFacebookClicked = {}
        ),
        uiNavigation = RegisterNavigationListener(
            onNavigateToLogin = {}
        )
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterStateListener,
    uiEvent: RegisterEventListener,
    uiNavigation: RegisterNavigationListener
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val name = remember { mutableStateOf(EMPTY_STRING) }
    val email = remember { mutableStateOf(EMPTY_STRING) }
    val phone = remember { mutableStateOf(EMPTY_STRING) }
    val password = remember { mutableStateOf(EMPTY_STRING) }
    val confirmPassword = remember { mutableStateOf(EMPTY_STRING) }

    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }

    var photoBase64 by remember { mutableStateOf(EMPTY_STRING) }
    val showPhotoPreview = remember { mutableStateOf(false) }

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

    LaunchedEffect(Unit) {
        name.value = Constant.TestData.TESTDATA_NAME
        email.value = Constant.TestData.TESTDATA_EMAIL
        phone.value = Constant.TestData.TESTDATA_PHONE
        password.value = Constant.TestData.TESTDATA_PASSWORD
        confirmPassword.value = Constant.TestData.TESTDATA_PASSWORD
    }

    if (uiState.registerByEmailState is ResourceFirebase.Success) {
        DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.SUCCESS,
                title = stringResource(R.string.success),
                message = stringResource(R.string.success_register),
                primaryButtonText = OK_STRING
            ),
            onDismiss = {
                uiNavigation.onNavigateToLogin()
            }
        )
    }

    if (uiState.registerByGoogleState is Resource.Success ||
        uiState.registerByFacebookState is Resource.Success
    ) {
        DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.WARNING,
                title = WARNING_STRING,
                message = stringResource(R.string.under_maintenance),
                primaryButtonText = OK_STRING
            ),
            onDismiss = { }
        )
    }

    if (showPhotoPreview.value) {
        Dialog(onDismissRequest = { showPhotoPreview.value = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable { showPhotoPreview.value = false },
                contentAlignment = Alignment.Center
            ) {
                AvatarImage(
                    bitmap = avatarBitmap.value,
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
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = Constant.Title.SIGN_UP,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                    AvatarImage(
                        bitmap = avatarBitmap.value,
                        modifier = Modifier.size(80.dp),
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

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                placeholder = { Text(Constant.Title.FULL_NAME) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                placeholder = { Text(Constant.Title.EMAIL) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                placeholder = { Text(Constant.Title.PHONE) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                placeholder = { Text(Constant.Title.PASSWORD) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                trailingIcon = {
                    val icon = if (passwordVisible.value)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible.value) Constant.Title.HIDE_PASSWORD else Constant.Title.SHOW_PASSWORD,
                        modifier = Modifier
                            .clickable {
                                passwordVisible.value = !passwordVisible.value
                            }
                            .padding(end = 12.dp)
                    )
                },
                visualTransformation =
                    if (passwordVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LockPerson,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                placeholder = { Text(Constant.Title.CONFIRM_PASSWORD) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                trailingIcon = {
                    val icon = if (confirmPasswordVisible.value)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    Icon(
                        imageVector = icon,
                        contentDescription = if (confirmPasswordVisible.value) Constant.Title.HIDE_PASSWORD else Constant.Title.SHOW_PASSWORD,
                        modifier = Modifier
                            .clickable {
                                confirmPasswordVisible.value = !confirmPasswordVisible.value
                            }
                            .padding(end = 12.dp)
                    )
                },
                visualTransformation =
                    if (confirmPasswordVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    uiEvent.onRegisterByEmailClicked(
                        name.value,
                        email.value,
                        phone.value,
                        password.value,
                        photoBase64
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
                Text(Constant.Title.CREATE_ACCOUNT, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = Constant.Title.OR_CONNECT_WITH,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1877F2)
                    ),
                    onClick = { uiEvent.onRegisterByFacebookClicked() }
                ) {
                    Text(Constant.Title.FACEBOOK)
                }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDB4437)
                    ),
                    onClick = { uiEvent.onRegisterByGoogleClicked() }
                ) {
                    Text(Constant.Title.GOOGLE)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = Constant.Title.ALREADY_HAVE_ACCOUNT,
                    color = Color.White,
                    fontSize = 12.sp
                )
                Text(
                    text = Constant.Title.LOGIN,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        uiNavigation.onNavigateToLogin()
                    }
                )
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
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale
        )
    } else {
        Image(
            painter = painterResource(R.drawable.ic_avatar),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}
