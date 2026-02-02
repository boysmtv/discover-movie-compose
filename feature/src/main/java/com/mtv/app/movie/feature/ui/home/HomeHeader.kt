package com.mtv.app.movie.feature.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.common.R
import androidx.compose.ui.unit.sp
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.EMPTY_STRING

@Preview(showBackground = true)
@Composable
fun PreviewHomeHeader() {
    HomeHeader(
        userName = "William B.",
        profileImage = R.drawable.ic_avatar,
        onNotificationClick = { }
    )
}

@Composable
fun HomeHeader(
    userName: String,
    profileImage: Int,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(profileImage),
                contentDescription = EMPTY_STRING,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = stringResource(R.string.hello),
                    color = Color.White.copy(0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = userName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f))
                .clickable { onNotificationClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = EMPTY_STRING,
                tint = Color.White.copy(0.8f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
