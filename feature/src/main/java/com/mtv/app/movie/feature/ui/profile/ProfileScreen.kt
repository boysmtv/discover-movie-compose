/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: ProfileScreen.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.53
 */

package com.mtv.app.movie.feature.ui.profile

import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.base64ToBitmap
import com.mtv.app.movie.feature.event.profile.ProfileDataListener
import com.mtv.app.movie.feature.event.profile.ProfileEventListener
import com.mtv.app.movie.feature.event.profile.ProfileNavigationListener
import com.mtv.app.movie.feature.event.profile.ProfileStateListener
import com.mtv.based.core.network.utils.Resource

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        uiState = ProfileStateListener(),
        uiData = ProfileDataListener(),
        uiEvent = ProfileEventListener(),
        uiNavigation = ProfileNavigationListener()
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileStateListener,
    uiData: ProfileDataListener,
    uiEvent: ProfileEventListener,
    uiNavigation: ProfileNavigationListener
) {
    val isPreview = LocalInspectionMode.current
    val photoBase64 = uiData.userAccount?.photo
    val avatarBitmap = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(photoBase64) {
        if (!isPreview && !photoBase64.isNullOrBlank()) {
            avatarBitmap.value = base64ToBitmap(photoBase64)
        }
    }

    LaunchedEffect(uiState.logoutState) {
        if (uiState.logoutState is Resource.Success) {
            uiNavigation.navigateToLoginAndClearBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF2F5))
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(48.dp))

        ProfileHeaderSection(
            name = uiData.userAccount?.name ?: uiData.userDummy.name,
            email = uiData.userAccount?.email ?: uiData.userDummy.email,
            photoBitmap = avatarBitmap.value
        )

        Spacer(modifier = Modifier.weight(1f))

        ProfileMenuCard(
            uiEvent = uiEvent,
            uiNavigation = uiNavigation
        )
    }
}

@Composable
fun ProfileHeaderSection(
    name: String,
    email: String,
    photoBitmap: Bitmap?
) {
    var showPreview by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.BottomEnd) {
        AvatarImage(
            bitmap = photoBitmap,
            modifier = Modifier
                .size(100.dp)
                .clickable(enabled = photoBitmap != null) {
                    showPreview = true
                },
            contentScale = ContentScale.Crop
        )
    }

    if (showPreview && photoBitmap != null) {
        Dialog(
            onDismissRequest = { showPreview = false },
            properties = androidx.compose.ui.window.DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable { showPreview = false },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = photoBitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    Spacer(Modifier.height(24.dp))

    Text(
        text = name,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(24.dp))

    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFDCE9FF),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = email,
            color = Color(0xFF2563EB),
            fontSize = 14.sp
        )
    }
}

@Composable
fun ProfileMenuCard(
    uiEvent: ProfileEventListener,
    uiNavigation: ProfileNavigationListener
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column {
            MenuRow(
                title = stringResource(R.string.edit_profile),
                icon = Icons.Outlined.Person,
                onClick = uiNavigation.onNavigateToEditProfile
            )

            HorizontalDivider()

            MenuRow(
                title = stringResource(R.string.add_pin),
                icon = Icons.Outlined.Lock,
                onClick = uiNavigation.onNavigateToAddPin
            )

            HorizontalDivider()

            MenuRow(
                title = stringResource(R.string.settings),
                icon = Icons.Outlined.Settings,
                onClick = uiNavigation.onNavigateToSettings
            )

            HorizontalDivider()

            MenuRow(
                title = stringResource(R.string.logout),
                icon = Icons.AutoMirrored.Outlined.ExitToApp,
                onClick = uiEvent.onLogout
            )
        }
    }
}

@Composable
fun MenuRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 18.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF2563EB)
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "›",
            fontSize = 24.sp,
            color = Color.Gray
        )
    }
}

@Composable
private fun AvatarImage(
    bitmap: Bitmap?,
    modifier: Modifier,
    contentScale: ContentScale
) {
    val imageModifier = modifier
        .clip(CircleShape)
        .background(Color.LightGray)

    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = imageModifier,
            contentScale = contentScale
        )
    } else {
        Image(
            painter = painterResource(R.drawable.ic_avatar),
            contentDescription = null,
            modifier = imageModifier,
            contentScale = contentScale
        )
    }
}