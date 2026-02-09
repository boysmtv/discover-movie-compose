plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.mtv.app.movie.common"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.prod.com/\"")
            buildConfigField("Boolean", "USE_KTOR", "true")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"http://192.168.68.127:8080/\"")
            buildConfigField("Boolean", "USE_KTOR", "true")

            // Tmdb Api
            buildConfigField("String", "TMDB_URL", "\"https://api.themoviedb.org/\"")
            buildConfigField("String", "TMDB_IMAGE_URL", "\"https://image.tmdb.org/t/p/w500/\"")

            // Credential
            buildConfigField("String", "TMDB_BEARER", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZmIzYTg2OWRkYTIyNjRjNDQ4MjYxY2Q4YWRlMjExMCIsIm5iZiI6MTY2MDA0NTA3My44ODgsInN1YiI6IjYyZjI0NzExMTUxMWFhMDA3YTdjZjNlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.B_f1VYeJoWUsgw6h2T91pfBIgLkIOwogLR7k6dxM4pU\"")
            buildConfigField("String", "TMDB_YOUTUBE_URL", "\"https://www.youtube.com/watch?v=\"")

            // Firebase
            buildConfigField("String", "FIREBASE_PROJECT_ID", "\"app-movie-e85f3\"")
            buildConfigField("String", "FIREBASE_USERS_COLLECTION", "\"users\"")
            buildConfigField("String", "FIREBASE_DEVICE_COLLECTION", "\"device\"")
            buildConfigField("String", "FIREBASE_DEVICE_LOG_COLLECTION", "\"device_log\"")
            buildConfigField("String", "FIREBASE_LOGIN_LOG_COLLECTION", "\"login_log\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

}
dependencies {
    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // Retrofit + Moshi + OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp)
    implementation(libs.retrofit.scalars)

    // Gson
    implementation(libs.gson)
    implementation(libs.retrofit.converter.gson)

    // Ktor Client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.exifinterface)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // AndroidX Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // AndroidX Core
    implementation(libs.androidx.core.ktx)

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.material3)
    implementation(libs.material3)
    implementation(libs.material.icons.extended)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    /* Maven Local Libraries (Core) */
    implementation("com.mtv.based.core:network:1.0.0")
    implementation("com.mtv.based.core:provider:1.0.0")

    /* Maven Local Libraries (UI Components) */
    implementation("com.mtv.based.uicomponent:component:1.0.0")
    implementation("com.mtv.based.uicomponent:ui:1.0.0")
    implementation("com.mtv.based.uicomponent:theme-ui:1.0.0")
}
