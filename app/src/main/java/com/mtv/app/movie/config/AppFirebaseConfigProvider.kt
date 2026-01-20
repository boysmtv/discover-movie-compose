package com.mtv.app.movie.config

import com.mtv.app.movie.common.BuildConfig
import com.mtv.based.core.network.config.FirebaseConfig
import com.mtv.based.core.network.config.FirebaseConfigProvider

class AppFirebaseConfigProvider : FirebaseConfigProvider {

    override fun provide(): FirebaseConfig =
        FirebaseConfig(
            projectId = BuildConfig.FIREBASE_PROJECT_ID,
            defaultCollection = BuildConfig.FIREBASE_DEFAULT_CONNECTION
        )

}