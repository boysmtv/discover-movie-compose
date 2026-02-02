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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.common.R
import com.mtv.app.movie.feature.event.splash.SplashEventListener
import com.mtv.app.movie.feature.event.splash.SplashNavigationListener
import com.mtv.app.movie.feature.event.splash.SplashStateListener
import com.mtv.based.core.network.utils.ResourceFirebase

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        uiState = SplashStateListener(),
        uiEvent = SplashEventListener {},
        uiNavigation = SplashNavigationListener {}
    )
}

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                stringResource(R.string.movie_app),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary.copy(alpha = alpha.value)
            )

            Spacer(Modifier.height(12.dp))

            PulsingLoadingText()
        }
    }
}

@Composable
fun PulsingLoadingText(text: String = "Loading") {
    val alpha = remember { Animatable(0.3f) }

    LaunchedEffect(Unit) {
        while (true) {
            alpha.animateTo(1f, tween(600))
            alpha.animateTo(0.3f, tween(600))
        }
    }

    Text(
        text = text,
        fontSize = 14.sp,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = alpha.value)
    )
}
