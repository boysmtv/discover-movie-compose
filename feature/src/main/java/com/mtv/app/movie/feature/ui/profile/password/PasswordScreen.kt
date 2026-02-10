/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: PasswordScreen.kt
 *
 * Last modified by Dedy Wijaya on 06/02/26 13.22
 */

package com.mtv.app.movie.feature.ui.profile.password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.LockPerson
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.common.Constant.Title.CHANGE_PASSWORD
import com.mtv.app.movie.common.Constant.Title.CONFIRM_NEW_PASSWORD
import com.mtv.app.movie.common.Constant.Title.CURRENT_PASSWORD
import com.mtv.app.movie.common.Constant.Title.ENTER_YOUR_NEW_CONFIRM_PASSWORD
import com.mtv.app.movie.common.Constant.Title.ENTER_YOUR_NEW_PASSWORD
import com.mtv.app.movie.common.Constant.Title.ENTER_YOUR_PASSWORD
import com.mtv.app.movie.common.Constant.Title.NEW_PASSWORD
import com.mtv.app.movie.common.Constant.Title.SAVE_CHANGES
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.ui.BasePasswordInput
import com.mtv.app.movie.common.ui.BaseTextInput
import com.mtv.app.movie.common.ui.BaseToolbar
import com.mtv.app.movie.common.ui.BasePrimaryButton
import com.mtv.app.movie.feature.event.profile.PasswordDialog
import com.mtv.app.movie.feature.event.profile.PasswordEventListener
import com.mtv.app.movie.feature.event.profile.PasswordNavigationListener
import com.mtv.app.movie.feature.event.profile.PasswordStateListener
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.WARNING_STRING

@Composable
fun PasswordScreen(
    uiState: PasswordStateListener,
    uiEvent: PasswordEventListener,
    uiNavigation: PasswordNavigationListener
) {
    val scrollState = rememberScrollState()

    val oldPassword = remember { mutableStateOf(EMPTY_STRING) }
    val newPassword = remember { mutableStateOf(EMPTY_STRING) }
    val newPasswordConfirm = remember { mutableStateOf(EMPTY_STRING) }

    val isFormValid = remember {
        derivedStateOf {
            oldPassword.value.isNotBlank()
                    && newPassword.value.isNotBlank()
                    && newPasswordConfirm.value.isNotBlank()
        }
    }

    uiState.activeDialog?.let { dialog ->
        when (dialog) {
            is PasswordDialog.Success -> {
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

            is PasswordDialog.Validate -> {
                DialogCenterV1(
                    state = DialogStateV1(
                        type = DialogType.WARNING,
                        title = WARNING_STRING,
                        message = dialog.message,
                        primaryButtonText = OK_STRING
                    ),
                    onDismiss = {
                        uiEvent.onDismissActiveDialog()
                    }
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
            title = CHANGE_PASSWORD,
            onLeftClick = { uiNavigation.onBack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BaseTextInput(
                label = CURRENT_PASSWORD,
                value = oldPassword.value,
                onValueChange = { oldPassword.value = it },
                placeholder = ENTER_YOUR_PASSWORD,
                leadingIcon = Icons.Default.LockOpen,
                isPassword = true
            )

            Spacer(Modifier.height(16.dp))

            BasePasswordInput(
                label = NEW_PASSWORD,
                value = newPassword.value,
                onValueChange = { newPassword.value = it },
                placeholder = ENTER_YOUR_NEW_PASSWORD,
                leadingIcon = Icons.Default.Lock
            )

            Spacer(Modifier.height(16.dp))

            BasePasswordInput(
                label = CONFIRM_NEW_PASSWORD,
                value = newPasswordConfirm.value,
                onValueChange = { newPasswordConfirm.value = it },
                placeholder = ENTER_YOUR_NEW_CONFIRM_PASSWORD,
                leadingIcon = Icons.Default.LockPerson
            )

            Spacer(Modifier.height(32.dp))

            BasePrimaryButton(
                text = SAVE_CHANGES,
                enabled = isFormValid.value,
                onClick = {
                    uiEvent.onSubmitPassword(
                        oldPassword.value,
                        newPassword.value,
                        newPasswordConfirm.value
                    )
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun PasswordScreenPreview() {
    MaterialTheme {
        PasswordScreen(
            uiState = PasswordStateListener(),
            uiEvent = PasswordEventListener({ _, _, _ -> }, {}),
            uiNavigation = PasswordNavigationListener(),
        )
    }
}