/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: MovieTagChip.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.07
 */

package com.mtv.app.movie.feature.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MovieTagChipAnimated(tag: MovieTag?) {
    AnimatedVisibility(
        visible = tag != null,
        enter = fadeIn() + scaleIn(initialScale = 0.85f),
        exit = fadeOut() + scaleOut(targetScale = 0.85f)
    ) {
        tag?.let {
            Text(
                text = it.label,
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier
                    .background(
                        color = it.color.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}
