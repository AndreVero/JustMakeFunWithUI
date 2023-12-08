package com.vero.justmakefunwithui.components.baseclock

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.lang.Math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(
    radius: Dp,
    modifier: Modifier
) {
    var center by remember {
        mutableStateOf(Offset.Zero)
    }

    var currentSeconds by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = true) {
        while (isActive) {
            delay(1000)
            currentSeconds += 1
        }
    }

    Canvas(modifier = modifier) {
        center = this.center
        drawContext.canvas.nativeCanvas.drawCircle(
            center.x,
            center.y,
            radius.toPx(),
            Paint().apply {
                strokeWidth = 0.dp.toPx()
                color = android.graphics.Color.WHITE
                style = Paint.Style.FILL
                setShadowLayer(
                    60f,
                    0f,
                    0f,
                    android.graphics.Color.argb(50, 0,0,0)
                )
            }
        )
        for (i in 0 until 60) {
            val angleInRad = (i * 6 - 90) * ((PI / 180f).toFloat())

            val lineStart = Offset(
                x = (radius.toPx() - 15.dp.toPx()) * cos(angleInRad) + center.x,
                y = (radius.toPx() - 15.dp.toPx()) * sin(angleInRad) + center.y
            )
            val lineEnd = Offset(
                x = radius.toPx() * cos(angleInRad) + center.x,
                y = radius.toPx() * sin(angleInRad) + center.y
            )
                drawLine(
                    color = Color.Black,
                    start = lineStart,
                    end = lineEnd,
                    strokeWidth = 1.dp.toPx()
                )

        }

        val angleInRad = (currentSeconds * 6 - 90) * ((PI / 180f).toFloat())

        val lineStart = Offset(
            x = center.x,
            y = center.y
        )
        val lineEnd = Offset(
            x = (radius.toPx() - 30.dp.toPx()) * cos(angleInRad) + center.x,
            y = (radius.toPx() - 30.dp.toPx()) * sin(angleInRad) + center.y
        )

        drawLine(
            color = Color.Red,
            start = lineEnd,
            end = lineStart,
            strokeWidth = 3.dp.toPx()
        )
    }
}