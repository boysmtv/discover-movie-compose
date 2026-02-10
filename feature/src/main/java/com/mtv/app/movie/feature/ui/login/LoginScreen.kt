/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LoginScreen.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.53
 */

package com.mtv.app.movie.feature.ui.login

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.ui.BaseTextInput
import com.mtv.app.movie.common.ui.BasePrimaryButton
import com.mtv.app.movie.common.ui.BaseSocialButton
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.feature.contract.LoginDialog
import com.mtv.app.movie.feature.contract.LoginEventListener
import com.mtv.app.movie.feature.contract.LoginNavigationListener
import com.mtv.app.movie.feature.contract.LoginStateListener
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.WARNING_STRING

@Composable
fun LoginScreen(
    uiState: LoginStateListener,
    uiEvent: LoginEventListener,
    uiNavigation: LoginNavigationListener
) {
    val username = remember { mutableStateOf(EMPTY_STRING) }
    val password = remember { mutableStateOf(EMPTY_STRING) }

    val isFormValid = remember {
        derivedStateOf {
            username.value.isNotBlank() && password.value.isNotBlank()
        }
    }

    LaunchedEffect(Unit) {
        username.value = Constant.TestData.TESTDATA_EMAIL
        password.value = Constant.TestData.TESTDATA_PASSWORD
    }

    LaunchedEffect(uiState.loginByEmailState) {
        if (uiState.loginByEmailState is ResourceFirebase.Success) {
            uiNavigation.onNavigateToHome()
        }
    }

    uiState.activeDialog?.let { dialog ->
        when (dialog) {
            is LoginDialog.Maintenance -> {
                DialogCenterV1(
                    state = DialogStateV1(
                        type = DialogType.WARNING,
                        title = WARNING_STRING,
                        message = dialog.message,
                        primaryButtonText = OK_STRING
                    ),
                    onDismiss = { uiEvent.onDismissActiveDialog() }
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Enter Your Space",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Log into to explore and bring your creatives ideas to life on MindBloom, where inspiration meets.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            BaseTextInput(
                label = Constant.Title.EMAIL,
                value = username.value,
                onValueChange = { username.value = it },
                placeholder = Constant.Title.ENTER_YOUR_EMAIL,
                leadingIcon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(12.dp))

            BaseTextInput(
                label = Constant.Title.PASSWORD,
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = Constant.Title.ENTER_YOUR_PASSWORD,
                leadingIcon = Icons.Default.Lock,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = Constant.Title.FORGOT_YOUR_PASSWORD,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        uiNavigation.onNavigateToForgotPassword()
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            BasePrimaryButton(
                text = Constant.Title.LOGIN,
                enabled = isFormValid.value,
                onClick = {
                    uiEvent.onLoginByEmailClicked(
                        username.value,
                        password.value
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = Constant.Title.OR_CONNECT_WITH,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BaseSocialButton(
                    text = Constant.Title.FACEBOOK,
                    iconRes = R.drawable.ic_facebook,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        uiEvent.onLoginByFacebookClicked()
                    }
                )
                BaseSocialButton(
                    text = Constant.Title.GOOGLE,
                    iconRes = R.drawable.ic_google,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        uiEvent.onLoginByGoogleClicked()
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = Constant.Title.DONT_HAVE_ACCOUNT,
                    color = Color.Black,
                    fontSize = 12.sp
                )
                Text(
                    text = Constant.Title.SIGN_UP_MARK,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        uiNavigation.onNavigateToSignUpByEmail()
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        uiState = LoginStateListener(
            loginByEmailState = ResourceFirebase.Success(
                LoginResponse(
                    name = "Dedy Wijaya",
                    email = "Dedy.wijaya@ikonsultan.com",
                    phone = "08158844424",
                    photo = "https://i.pinimg.com/736x/41/66/b0/4166b08e8d32aff9e00f2bee5e2dc4dd.jpg",
                    createdAt = "21/12/26"
                )
            ),
        ),
        uiEvent = LoginEventListener(
            onLoginByEmailClicked = { _, _ -> },
            onLoginByGoogleClicked = { },
            onLoginByFacebookClicked = { },
            onDismissActiveDialog = { },
        ),
        uiNavigation = LoginNavigationListener(
            onNavigateToHome = {},
            onNavigateToSignUpByEmail = {},
            onNavigateToForgotPassword = {},
        )
    )
}