/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: BaseToolbar.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 11.11
 */

package com.mtv.app.movie.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun BaseToolbar(
    title: String,
    modifier: Modifier = Modifier,
    showLeftIcon: Boolean = true,
    showRightIcon: Boolean = false,
    leftIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    rightIcon: ImageVector = Icons.Default.MoreVert,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFF5F5F5))
    ) {

        if (showLeftIcon) {
            IconButton(
                onClick = onLeftClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 4.dp)
            ) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = "Left Icon"
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Center),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (showRightIcon) {
            IconButton(
                onClick = onRightClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 4.dp)
            ) {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = "Right Icon"
                )
            }
        }
    }
}