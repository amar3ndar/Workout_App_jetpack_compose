package com.example.workout_app_jetpack_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(onStartClick: () -> Unit) {
    KeepScreenOn()
    Column(
        modifier = Modifier
            .fillMaxSize()//.background(MaterialTheme.colorScheme.onBackground)
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image at the top
        Image(
            painter = painterResource(id = R.drawable.ic_7min_main_ui),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        // Spacer to center the button
        Spacer(modifier = Modifier.weight(1f))

        // Start button in a circular with background and clickable
        Box(
            modifier = Modifier
                .size(150.dp) // Ensure width and height are equal
                .clip(CircleShape) // Make it circular
                .background(color = colorResource(R.color.white)) // Set background color
                .border(4.dp, color = colorResource(R.color.colorAccent), CircleShape)
                .clickable { onStartClick() }, // Handle click event
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "START",
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Spacer after the button
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun KeepScreenOn() {
    val view = LocalView.current
    DisposableEffect(Unit) {
        view.keepScreenOn = true
        onDispose {
            view.keepScreenOn = false
        }
    }
}