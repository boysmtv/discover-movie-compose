package com.mtv.app.movie.feature.ui.login

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.common.R
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.feature.event.login.LoginEventListener
import com.mtv.app.movie.feature.event.login.LoginNavigationListener
import com.mtv.app.movie.feature.event.login.LoginStateListener
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
        ),
        uiNavigation = LoginNavigationListener(
            onNavigateToHome = {},
            onNavigateToSignUpByEmail = {},
            onNavigateToForgotPassword = {},
        )
    )
}

@Composable
fun LoginScreen(
    uiState: LoginStateListener,
    uiEvent: LoginEventListener,
    uiNavigation: LoginNavigationListener
) {
    val username = remember { mutableStateOf(EMPTY_STRING) }
    val password = remember { mutableStateOf(EMPTY_STRING) }
    val passwordVisible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        username.value = Constant.TestData.TESTDATA_EMAIL
        password.value = Constant.TestData.TESTDATA_PASSWORD
    }

    LaunchedEffect(uiState.loginByEmailState) {
        if (uiState.loginByEmailState is ResourceFirebase.Success) {
            uiNavigation.onNavigateToHome()
        }
    }

    if (uiState.loginByGoogleState is Resource.Success ||
        uiState.loginByFacebookState is Resource.Success
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB39DDB),
                        Color(0xFF7986CB)
                    )
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
                text = Constant.Title.LOGIN,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                placeholder = { Text(Constant.Title.USERNAME) },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
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
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                trailingIcon = {
                    val icon = if (passwordVisible.value)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible.value) stringResource(R.string.hide_password) else stringResource(
                            R.string.show_password
                        ),
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    uiEvent.onLoginByEmailClicked(username.value, password.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5C6BC0)
                )
            ) {
                Text(Constant.Title.LOGIN, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = Constant.Title.FORGOT_YOUR_PASSWORD,
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.clickable {
                    uiNavigation.onNavigateToForgotPassword()
                }
            )

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
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1877F2)
                    ),
                    shape = RoundedCornerShape(50),
                    onClick = { uiEvent.onLoginByFacebookClicked() },
                ) {
                    Text(Constant.Title.FACEBOOK)
                }

                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDB4437)
                    ),
                    shape = RoundedCornerShape(50),
                    onClick = { uiEvent.onLoginByGoogleClicked() },
                ) {
                    Text(Constant.Title.GOOGLE)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = Constant.Title.DONT_HAVE_ACCOUNT,
                    color = Color.White,
                    fontSize = 12.sp
                )
                Text(
                    text = Constant.Title.SIGN_UP_MARK,
                    color = Color.White,
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