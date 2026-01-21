package com.mtv.app.movie.feature.ui.register

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.feature.event.register.RegisterEventListener
import com.mtv.app.movie.feature.event.register.RegisterNavigationListener
import com.mtv.app.movie.feature.event.register.RegisterStateListener
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.ErrorDialogStateV1

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(
        uiState = RegisterStateListener(),
        uiEvent = RegisterEventListener(
            onRegisterClicked = { _, _, _, _ -> }
        ),
        uiNavigation = RegisterNavigationListener(
            onNavigateToLogin = {},
            onRegisterByGoogle = {},
            onRegisterByFacebook = {},
        )
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterStateListener,
    uiEvent: RegisterEventListener,
    uiNavigation: RegisterNavigationListener
) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        name.value = Constant.TestData.NAME
        email.value = Constant.TestData.EMAIL
        phone.value = Constant.TestData.PHONE
        password.value = Constant.TestData.PASSWORD
        confirmPassword.value = Constant.TestData.PASSWORD
    }

    if (uiState.registerState is ResourceFirebase.Success) {
        DialogCenterV1(
            state = ErrorDialogStateV1(
                title = "Success",
                message = "Success Register",
                primaryButtonText = "OK"
            )

        ) {
            uiNavigation.onNavigateToLogin()
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
                text = Constant.Title.SIGN_UP,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                placeholder = { Text(Constant.Description.FULL_NAME) },
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
                        contentDescription = if (passwordVisible.value) "Hide Password" else "Show Password",
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
                placeholder = { Text(Constant.Description.CONFIRM_PASSWORD) },
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
                        contentDescription = if (confirmPasswordVisible.value) Constant.Description.HIDE_PASSWORD else Constant.Description.SHOW_PASSWORD,
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
                    uiEvent.onRegisterClicked(
                        name.value,
                        email.value,
                        phone.value,
                        password.value
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
                Text(Constant.Description.CREATE_ACCOUNT, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = Constant.Description.OR_CONNECT_WITH,
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
                    onClick = { uiNavigation.onRegisterByFacebook() }
                ) {
                    Text(Constant.Title.FACEBOOK)
                }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDB4437)
                    ),
                    onClick = { uiNavigation.onRegisterByGoogle() }
                ) {
                    Text(Constant.Title.GOOGLE)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = Constant.Description.ALREADY_HAVE_ACCOUNT,
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
