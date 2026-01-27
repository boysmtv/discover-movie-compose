package com.mtv.app.movie.feature.ui.reset

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.feature.event.register.RegisterEventListener
import com.mtv.app.movie.feature.event.register.RegisterNavigationListener
import com.mtv.app.movie.feature.event.register.RegisterStateListener
import com.mtv.app.movie.feature.event.reset.ResetEventListener
import com.mtv.app.movie.feature.event.reset.ResetNavigationListener
import com.mtv.app.movie.feature.event.reset.ResetStateListener
import com.mtv.based.core.network.utils.ResourceFirebase
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.ErrorDialogStateV1

@Preview(showBackground = true)
@Composable
fun PreviewResetScreen() {
    ResetScreen(
        uiState = ResetStateListener(),
        uiEvent = ResetEventListener(
            onResetPasswordClicked = { _ -> }
        ),
        uiNavigation = ResetNavigationListener(
            onNavigateToLogin = {},
            onRegisterByGoogle = {},
            onRegisterByFacebook = {},
        )
    )
}

@Composable
fun ResetScreen(
    uiState: ResetStateListener,
    uiEvent: ResetEventListener,
    uiNavigation: ResetNavigationListener
) {
    val email = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        email.value = Constant.TestData.EMAIL
    }

    if (uiState.resetPasswordState is ResourceFirebase.Success) {
        DialogCenterV1(
            state = ErrorDialogStateV1(
                title = "Warning",
                message = "Reset Password Successfully",
                primaryButtonText = "OK"
            ),
            onDismiss = {
                uiNavigation.onNavigateToLogin()
            }
        )
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
                text = Constant.Title.FORGOT_PASSWORD,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Enter your email and we’ll send you a reset link.",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                placeholder = {
                    Text(Constant.Title.EMAIL)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5C6BC0)
                ),
                onClick = {
                    uiEvent.onResetPasswordClicked(
                        email.value
                    )
                },
            ) {
                Text("Send Reset Link", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "or reset via",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1877F2)
                    ),
                    onClick = {
                        uiNavigation.onRegisterByFacebook()
                    },
                ) {
                    Text(Constant.Title.FACEBOOK)
                }

                Button(
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDB4437)
                    ),
                    onClick = {
                        uiNavigation.onRegisterByGoogle()
                    },
                ) {
                    Text(Constant.Title.GOOGLE)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Text(
                    text = "Back to ",
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