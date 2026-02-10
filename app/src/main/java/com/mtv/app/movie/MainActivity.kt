package com.mtv.app.movie

import AppNavigation
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("LOG_BOYS_FCM", "Token: ${task.result}")
            } else {
                Log.e("LOG_BOYS_FCM", "Fetching FCM token failed", task.exception)
            }
        }

        setContent {
            AppNavigation()
        }
    }
}