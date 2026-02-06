package com.mtv.app.movie.feature.ui.play

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    device = Devices.PIXEL_3
)
@Composable
fun PlayMovieScreenPreview() {
    PlayMovieScreen(
        key = "O-b2VfmmbyA",
        onBack = {}
    )
}

@Composable
fun PlayMovieScreen(
    key: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    val view = LocalView.current
    val window = activity.window
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val isPreview = LocalInspectionMode.current

    LaunchedEffect(Unit) {
        activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, view).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            activity.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

            WindowInsetsControllerCompat(window, view)
                .show(WindowInsetsCompat.Type.systemBars())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        if (isPreview) {
            PreviewPlaceholder()
        } else {
            YouTubePlayerCompose(
                videoId = key,
                lifecycleOwner = lifecycle
            )
        }

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun YouTubePlayerCompose(
    videoId: String,
    lifecycleOwner: Lifecycle
) {
    val context = LocalContext.current

    val youTubePlayerView = remember {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
        }
    }

    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.addObserver(youTubePlayerView)

        onDispose {
            lifecycleOwner.removeObserver(youTubePlayerView)
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            youTubePlayerView.apply {
                initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(player: YouTubePlayer) {
                            player.loadVideo(videoId, 0f)
                        }
                    }
                )
            }
        }
    )
}


@Composable
private fun PreviewPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(80.dp)
        )
    }
}
