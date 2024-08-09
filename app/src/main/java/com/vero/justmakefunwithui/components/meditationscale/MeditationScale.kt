package com.vero.justmakefunwithui.components.meditationscale

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Composable
fun MovableCubicBezier() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animation for the wave's offset
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "wave_animation"
    )

    // Canvas to draw the wave
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val width = size.width
        val height = size.height / 2

        // Path to create the wave
        val path = Path().apply {
            moveTo(0f, height)

            // Draw a sinusoidal wave
            for (x in 0..width.toInt()) {
                val y = height + 50 * kotlin.math.sin((x + waveOffset) * (2 * Math.PI / 180)).toFloat()
                lineTo(x.toFloat(), y)
            }

            lineTo(width, height * 2)
            lineTo(0f, height * 2)
            close()
        }

        // Draw the wave
        drawPath(path, color = Color.Blue)
    }
}