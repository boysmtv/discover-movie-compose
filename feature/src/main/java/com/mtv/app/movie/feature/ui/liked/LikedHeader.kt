/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: LikedHeader.kt
 *
 * Last modified by Dedy Wijaya on 02/02/26 12.35
 */

package com.mtv.app.movie.feature.ui.liked

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.R
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING


@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun PreviewLikedHeader() {
    LikedHeader(
        onDeletedAllMovies = { }
    )
}

@Composable
fun LikedHeader(
    onDeletedAllMovies: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = stringResource(R.string.your_favorite),
            color = Color.DarkGray.copy(0.7f),
            fontSize = 20.sp
        )

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.DarkGray.copy(alpha = 0.1f))
                .clickable { onDeletedAllMovies() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.DeleteSweep,
                contentDescription = EMPTY_STRING,
                tint = Color.DarkGray.copy(0.8f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}