package com.mtv.app.movie.feature.event.profile

data class ProfileEventListener(
    val onEditProfile: () -> Unit = {},
    val onAddPin: () -> Unit = {},
    val onInviteFriend: () -> Unit = {},
    val onLogout: () -> Unit = {}
)