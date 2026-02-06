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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    device = Devices.PIXEL_3
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

    val isFormValid = remember {
        derivedStateOf {
            username.value.isNotBlank() && password.value.isNotBlank()
        }
    }

//    LaunchedEffect(Unit) {
//        username.value = Constant.TestData.TESTDATA_EMAIL
//        password.value = Constant.TestData.TESTDATA_PASSWORD
//    }

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
            .background(Color.White.copy(alpha = 0.95f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = Constant.Title.LOGIN,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(52.dp))

            Text(
                text = Constant.Title.USERNAME,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = username.value,
                onValueChange = { username.value = it },
                placeholder = {
                    Text(
                        text = Constant.Title.ENTER_YOUR_EMAIL,
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFFFFFFF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = Constant.Title.PASSWORD,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = {
                    Text(
                        text = Constant.Title.ENTER_YOUR_PASSWORD,
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (passwordVisible.value)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable {
                                passwordVisible.value = !passwordVisible.value
                            }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                visualTransformation =
                    if (passwordVisible.value)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color(0xFFFFFFFF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

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

            Button(
                onClick = {
                    uiEvent.onLoginByEmailClicked(username.value, password.value)
                },
                enabled = isFormValid.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5C6BC0),
                    disabledContainerColor = Color(0xFFD6D6D6)
                )
            ) {
                Text(
                    Constant.Title.LOGIN,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = Constant.Title.OR_CONNECT_WITH,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    onClick = { uiEvent.onLoginByFacebookClicked() },
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_facebook),
                            contentDescription = null,
                            tint = Color(0xFF1877F2),
                            modifier = Modifier.size(30.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = Constant.Title.FACEBOOK,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    onClick = { uiEvent.onLoginByGoogleClicked() },
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_google),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(30.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = Constant.Title.GOOGLE,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }

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
                    color = Color.Black,
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