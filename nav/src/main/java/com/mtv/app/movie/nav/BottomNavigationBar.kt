package com.mtv.app.movie.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Like,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.Black,
        modifier = Modifier
            .height(72.dp)
    ) {
        items.forEachIndexed { index, item ->

            val isSelected = currentRoute == item.route
            val isFirst = index == 0
            val isLast = index == items.lastIndex

            val chipWidth by animateDpAsState(
                targetValue = if (isSelected) 90.dp else 40.dp,
                animationSpec = tween(
                    durationMillis = 350,
                    easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)
                )
            )

            val chipColor by animateColorAsState(
                targetValue = if (isSelected) Color(0xFFFF8A00) else Color.Transparent,
                animationSpec = tween(350)
            )

            val labelAlpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0f,
                animationSpec = tween(300)
            )

            val iconTint by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color.White.copy(0.6f),
                animationSpec = tween(300)
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {

                    Row(
                        modifier = Modifier
                            .height(36.dp)
                            .width(chipWidth)
                            .clip(CircleShape)
                            .background(chipColor)
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.label,
                            tint = iconTint,
                            modifier = Modifier.size(22.dp)
                        )

                        AnimatedVisibility(
                            visible = labelAlpha > 0f,
                            enter = fadeIn(tween(200)),
                            exit = fadeOut(tween(200))
                        ) {
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = item.label,
                                color = Color.White.copy(alpha = labelAlpha),
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                modifier = Modifier.padding(
                    start = if (isFirst) 10.dp else 0.dp,
                    end = if (isLast) 10.dp else 0.dp
                )
            )
        }
    }
}
