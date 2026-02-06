/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: FormExtension.kt
 *
 * Last modified by Dedy Wijaya on 06/02/26 16.00
 */

package com.mtv.app.movie.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

val FieldBg = Color(0xFFF2F2F2)
val ButtonEnabled = Color(0xFF5C6BC0)
val ButtonDisabled = Color(0xFFD6D6D6)

@Composable
fun RoundedInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(placeholder, color = Color.Gray)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = FieldBg,
            unfocusedContainerColor = FieldBg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}