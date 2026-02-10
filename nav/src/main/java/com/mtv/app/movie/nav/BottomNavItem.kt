package com.mtv.app.movie.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem(route = "home", icon = Icons.Filled.Home, label = "Home")
    object Search : BottomNavItem(route = "search", icon = Icons.Filled.Search, label = "Search")
    object Like : BottomNavItem(route = "like", icon = Icons.Filled.Favorite, label = "Like")
    object Profile : BottomNavItem(route = "profile", icon = Icons.Filled.Person, label = "Profile")
}
