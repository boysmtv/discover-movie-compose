package com.mtv.app.movie.config

import com.mtv.app.movie.BuildConfig
import com.mtv.based.core.network.firebase.config.FirebaseConfig
import com.mtv.based.core.network.firebase.config.FirebaseConfigProvider

class AppFirebaseConfigProvider : FirebaseConfigProvider {

    override fun provide(): FirebaseConfig =
        FirebaseConfig(
            projectId = BuildConfig.FIREBASE_PROJECT_ID,
            defaultCollection = BuildConfig.FIREBASE_DEFAULT_CONNECTION
        )

}