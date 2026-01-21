package com.mtv.app.movie.feature.ui.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mtv.app.movie.feature.event.splash.SplashEventListener
import com.mtv.app.movie.feature.event.splash.SplashNavigationListener
import com.mtv.app.movie.feature.event.splash.SplashStateListener
import com.mtv.based.core.network.utils.ResourceFirebase

@Composable
fun SplashScreen(
    uiState: SplashStateListener,
    uiEvent: SplashEventListener,
    uiNavigation: SplashNavigationListener
) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {

        scale.animateTo(
            1.1f, animationSpec = tween(700, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        scale.animateTo(1f)
        alpha.animateTo(1f, tween(400))

        uiEvent.onDoSplash()
    }

    LaunchedEffect(uiState.splashState) {
        if (uiState.splashState is ResourceFirebase.Success) {
            uiNavigation.onNavigateToLogin()
        }
    }

    // UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                "My App",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary.copy(alpha = alpha.value)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f * alpha.value)
            )
        }
    }
}
