/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: BasePasswordForm.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 10.51
 */

package com.mtv.app.movie.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mtv.based.core.network.utils.ErrorMessages.WEAK_PASSWORD

private const val PASSWORD_REGEX =
    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])(?=\\S+\$).{8,}\$"

fun isValidPassword(password: String): Boolean {
    return PASSWORD_REGEX.toRegex().matches(password)
}

@Composable
fun BasePasswordInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val isError = value.isNotEmpty() && !isValidPassword(value)

    Column(modifier = modifier) {

        // Label
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleSmall,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = value,
            enabled = enabled,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.padding(start = 12.dp)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff,
                    contentDescription = null,
                    tint = if (isError)
                        MaterialTheme.colorScheme.error
                    else
                        Color.Gray,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            passwordVisible = !passwordVisible
                        }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            visualTransformation = if (!passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            isError = isError,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,

                focusedIndicatorColor = if (isError)
                    MaterialTheme.colorScheme.error
                else
                    Color.Transparent,
                unfocusedIndicatorColor = if (isError)
                    MaterialTheme.colorScheme.error
                else
                    Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = if (isError)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primary
            )
        )

        if (isError) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = WEAK_PASSWORD,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}