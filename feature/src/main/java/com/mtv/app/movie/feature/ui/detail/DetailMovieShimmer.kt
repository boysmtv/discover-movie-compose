/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: DetailMovieShimmer.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 12.37
 */

package com.mtv.app.movie.feature.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.feature.utils.shimmerEffect

@Composable
fun DetailMovieShimmer() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        item { VideoHeaderShimmer() }
        item { MovieInfoSectionShimmer() }
        item { PlayDownloadSectionShimmer() }
        item { DescriptionSectionShimmer() }
        item { ActionButtonsShimmer() }
    }
}

@Composable
fun VideoHeaderShimmer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
            .shimmerEffect()
    ) {
        // Back button placeholder
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(36.dp)
                .clip(CircleShape)
                .align(Alignment.TopStart)
                .shimmerEffect()
        )

        // Play button placeholder
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
                .shimmerEffect()
        )
    }
}

@Composable
fun MovieInfoSectionShimmer() {
    Column(modifier = Modifier.padding(16.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(14.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun PlayDownloadSectionShimmer() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .shimmerEffect()
    )
}

@Composable
fun DescriptionSectionShimmer() {
    Column(modifier = Modifier.padding(16.dp)) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ActionButtonsShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(3) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
    device = Devices.PIXEL_4
)
@Composable
fun PreviewDetailMovieShimmer() {
    DetailMovieShimmer()
}
