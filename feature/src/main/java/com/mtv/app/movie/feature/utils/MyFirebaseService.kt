/*
 * Project: App Movie Compose
 * Author: Boys.mtv@gmail.com
 * File: MyFirebaseService.kt
 *
 * Last modified by Dedy Wijaya on 10/02/26 15.16
 */

package com.mtv.app.movie.feature.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mtv.app.core.provider.utils.SecurePrefs
import com.mtv.app.movie.common.ConstantPreferences.KEY_NOTIFICATIONS

data class NotificationItem(val title: String, val body: String)

class MyFirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.data["title"] ?: "No title"
        val body = message.data["message"] ?: "No message"

        Log.d("LOG-BOYS", "Message: ${message.data}")

        saveNotification(SecurePrefs(applicationContext), title, body)
        applicationContext.showNotification(title, body)
    }

    override fun onNewToken(token: String) {
        Log.d("LOG-BOYS", "New token: $token")
    }
}

fun saveNotification(securePrefs: SecurePrefs, title: String, body: String) {
    val current = getAllNotifications(securePrefs).toMutableList()
    current.add(0, NotificationItem(title, body))
    securePrefs.putObject(KEY_NOTIFICATIONS, current.toTypedArray())
}

fun getAllNotifications(securePrefs: SecurePrefs): List<NotificationItem> {
    return securePrefs.getObject(KEY_NOTIFICATIONS, Array<NotificationItem>::class.java)?.toList()
        ?: emptyList()
}

fun Context.showNotification(title: String, message: String, channelId: String = "default_channel") {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Default Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(this, channelId)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
}

