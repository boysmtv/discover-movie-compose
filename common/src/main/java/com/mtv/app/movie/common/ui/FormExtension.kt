/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: FormExtension.kt
 *
 * Last modified by Dedy Wijaya on 06/02/26 16.00
 */

package com.mtv.app.movie.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BaseTextInput(
    label: String,
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Text(
        text = label,
        fontWeight = FontWeight.SemiBold,
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
        trailingIcon = if (isPassword) {
            {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            passwordVisible = !passwordVisible
                        }
                )
            }
        } else null,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF5C6BC0),
            disabledContainerColor = Color(0xFFD6D6D6)
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
fun SocialButton(
    text: String,
    iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    contentColor: Color = Color.Black
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(50.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                color = contentColor,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun RatingStars(
    rating: Double,
    starSize: Dp = 14.dp,
    starColor: Color = Color(0xFFFF9800)
) {
    val maxStars = 5
    val safeRating = rating.coerceIn(0.0, 10.0)
    val ratingOnFive = (safeRating / 10.0) * maxStars
    val fullStars = ratingOnFive.toInt()
    val hasHalfStar = (ratingOnFive - fullStars) >= 0.5
    val emptyStars = maxStars - fullStars - if (hasHalfStar) 1 else 0

    Row(verticalAlignment = Alignment.CenterVertically) {

        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(starSize)
            )
        }

        if (hasHalfStar) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.StarHalf,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(starSize)
            )
        }

        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Filled.StarBorder,
                contentDescription = null,
                tint = starColor.copy(alpha = 0.4f),
                modifier = Modifier.size(starSize)
            )
        }
    }
}