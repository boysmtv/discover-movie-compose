/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: HomeShimmer.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 11.45
 */

package com.mtv.app.movie.feature.ui.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.feature.utils.shimmerEffect

@Composable
fun HomeFeaturedSectionShimmer() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(120.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(5) {
                HomeFeaturedMovieCardShimmer()
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun HomeFeaturedMovieCardShimmer() {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(8.dp))
            .shimmerEffect()
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
    device = Devices.PIXEL_4
)
@Composable
fun PreviewHomeFeaturedSectionShimmer() {
    HomeFeaturedSectionShimmer()
}
