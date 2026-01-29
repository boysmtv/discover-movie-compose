package com.mtv.app.movie.nav

import androidx.annotation.DrawableRes
import com.mtv.app.movie.common.R

sealed class BottomNavItem(
    val route: String,
    @DrawableRes val icon: Int,
    val label: String
) {
    object Home : BottomNavItem("home", R.drawable.ic_avatar, "Home")
    object Search : BottomNavItem("search", R.drawable.ic_avatar, "Search")
    object Like : BottomNavItem("like", R.drawable.ic_avatar, "Like")
    object Profile : BottomNavItem("profile", R.drawable.ic_avatar, "Profile")
}

