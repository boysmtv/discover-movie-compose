package com.mtv.app.movie.feature.ui.reset

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.Constant
import com.mtv.app.movie.common.R
import com.mtv.app.movie.feature.event.reset.ResetEventListener
import com.mtv.app.movie.feature.event.reset.ResetNavigationListener
import com.mtv.app.movie.feature.event.reset.ResetStateListener
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
    val email = remember { mutableStateOf(EMPTY_STRING) }

    LaunchedEffect(Unit) {
        email.value = Constant.TestData.TESTDATA_EMAIL
    }

    if (uiState.resetPasswordState is ResourceFirebase.Success) {
        DialogCenterV1(
            state = DialogStateV1(
                type = DialogType.SUCCESS,
                title = WARNING_STRING,
                message = stringResource(R.string.reset_password_success),
                primaryButtonText = OK_STRING
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
                text = stringResource(R.string.enter_your_email_reset_password),
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
                Text(stringResource(R.string.send_reset_link), fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.or_reset_via),
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
                    text = stringResource(R.string.back_to),
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