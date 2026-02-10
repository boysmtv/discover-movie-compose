/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: NotificationScreen.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 14.20
 */

package com.mtv.app.movie.feature.ui.notif

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.common.Constant.Title.NOTIFICATION
import com.mtv.app.movie.common.ui.BaseToolbar
import com.mtv.app.movie.feature.contract.NotificationEventListener
import com.mtv.app.movie.feature.contract.NotificationNavigationListener
import com.mtv.app.movie.feature.contract.NotificationStateListener

@Composable
fun NotificationScreen(
    uiState: NotificationStateListener,
    uiEvent: NotificationEventListener,
    uiNavigation: NotificationNavigationListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {

        BaseToolbar(
            title = NOTIFICATION,
            onLeftClick = { uiNavigation.onBack() }
        )

        Text(
            text = "Previously",
            style = MaterialTheme.typography.titleSmall,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(5) {
                NotificationItem()
            }
        }
    }
}


@Composable
fun NotificationItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {

        InitialAvatar(
            text = "Vista rewards club",
            size = 44.dp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vista rewards club...",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Dec 16, 2023",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(6.dp))

                UnreadDot()
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Earn Points without making a purchase. Complete your first mission today!",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun UnreadDot() {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(Color(0xFF1E88E5))
    )
}


@Composable
fun InitialAvatar(
    text: String,
    size: Dp = 44.dp,
    backgroundColor: Color = Color(0xFFFFC0CB),
    textColor: Color = Color.Black
) {
    val initial = remember(text) {
        text.trim().firstOrNull()?.uppercase()?.plus(".") ?: ""
    }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = textColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun MovieItemNetflixPreview() {
    NotificationItem()
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun SearchScreenPreview() {
    NotificationScreen(
        uiState = NotificationStateListener(),
        uiEvent = NotificationEventListener({}, {}),
        uiNavigation = NotificationNavigationListener({})
    )
}
