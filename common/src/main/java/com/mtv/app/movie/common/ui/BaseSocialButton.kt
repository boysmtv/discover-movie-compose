/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: BaseSocialButton.kt
 *
 * Last modified by Dedy Wijaya on 06/02/26 16.00
 */

package com.mtv.app.movie.common.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BaseSocialButton(
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