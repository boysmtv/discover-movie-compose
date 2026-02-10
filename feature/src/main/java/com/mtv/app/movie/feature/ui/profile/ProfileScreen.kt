/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: ProfileScreen.kt
 *
 * Last modified by Dedy Wijaya on 03/02/26 11.53
 */

package com.mtv.app.movie.feature.ui.profile

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mtv.app.movie.common.R
import com.mtv.app.movie.common.base64ToBitmap
import com.mtv.app.movie.feature.contract.ProfileDataListener
import com.mtv.app.movie.feature.contract.ProfileDialog
import com.mtv.app.movie.feature.contract.ProfileEventListener
import com.mtv.app.movie.feature.contract.ProfileNavigationListener
import com.mtv.app.movie.feature.contract.ProfileStateListener
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogStateV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogType
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.OK_STRING
import com.mtv.based.uicomponent.core.ui.util.Constants.Companion.WARNING_STRING

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

    LaunchedEffect(uiState.onLogoutState) {
        if (uiState.onLogoutState is Resource.Success) {
            uiNavigation.navigateToLoginAndClearBackStack()
        }
    }

    uiState.activeDialog?.let { dialog ->
        when (dialog) {
            is ProfileDialog.Maintenance -> {
                DialogCenterV1(
                    state = DialogStateV1(
                        type = DialogType.WARNING,
                        title = WARNING_STRING,
                        message = dialog.message,
                        primaryButtonText = OK_STRING
                    ),
                    onDismiss = { uiEvent.onDismissActiveDialog() }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarImage(
                bitmap = photoBitmap,
                modifier = Modifier
                    .size(80.dp)
                    .clickable(enabled = photoBitmap != null) {
                        showPreview = true
                    },
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    color = Color.DarkGray,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = email,
                    color = Color(0xFF2563EB),
                    fontSize = 14.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(2.dp)
                        .background(Color(0xFF5A3FD1).copy(alpha = 0.6f))
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "“Small steps taken consistently can lead to remarkable change.”",
                    color = Color(0xFF5A3FD1),
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
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
}

@Composable
fun ProfileMenuCard(
    uiEvent: ProfileEventListener,
    uiNavigation: ProfileNavigationListener
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
                title = stringResource(R.string.change_password),
                icon = Icons.Outlined.Lock,
                onClick = uiNavigation.onNavigateToChangePassword
            )

            HorizontalDivider()

            MenuRow(
                title = stringResource(R.string.settings),
                icon = Icons.Outlined.Settings,
                onClick = uiEvent.onNavigateToSettings
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

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
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