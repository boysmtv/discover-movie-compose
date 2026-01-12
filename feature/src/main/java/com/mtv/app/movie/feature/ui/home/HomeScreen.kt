package com.mtv.app.movie.feature.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import com.mtv.based.core.network.utils.ResourceFirebase

@Composable
fun HomeScreen(
    checkState: ResourceFirebase<CheckResponse>,
    logoutState: ResourceFirebase<LogoutResponse>,
    onDoCheck: (email: String) -> Unit,
    onDoLogout: (email: String) -> Unit,
    onSuccessLogout: () -> Unit,
) {

    LaunchedEffect(Unit) {
        if (logoutState is ResourceFirebase.Success) {
            onSuccessLogout()
        }
        onDoCheck("Boys.mtv@gmail.com")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF7986CB),
                        Color(0xFF5C6BC0)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Home",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (checkState is ResourceFirebase.Success) {
                Text(
                    text = "Last Check-in",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = checkState.data.date,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    onDoLogout("Boys.mtv@gmail.com")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                )
            ) {
                Text(
                    text = "Logout",
                    fontSize = 16.sp
                )
            }
        }

        when (logoutState) {
            is ResourceFirebase.Success -> {
                LaunchedEffect(Unit) {
                    onSuccessLogout()
                }
            }

            else -> {}
        }

    }
}
