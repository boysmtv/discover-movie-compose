/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: SearchShimmer.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 11.55
 */

package com.mtv.app.movie.feature.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
fun SearchShimmer() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
    ) {
        items(5) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect()
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .width(80.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .width(120.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                                .height(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .height(1.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                        .background(Color.LightGray.copy(alpha = 0.6f))
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
fun PreviewSearchShimmer() {
    SearchShimmer()
}
