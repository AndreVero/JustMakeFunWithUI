package com.vero.justmakefunwithui.components.basetictactoe

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Position(
    val x: Float,
    val y: Float,
    val isOval : Boolean,
)

@Composable
fun TicTacToe(
    size: Dp,
    modifier: Modifier = Modifier,
) {

    var rectOffset by remember { mutableStateOf(Offset.Zero) }
    var rects by remember { mutableStateOf<Array<Array<Rect>>?>(null) }
    val results = remember { mutableStateListOf<Position>() }

    Canvas(modifier = modifier
        .pointerInput(true) {
            detectTapGestures { offset ->
                val transformedMaleRect =
                    Rect(
                        offset = rectOffset,
                        size = Size(size.toPx(), size.toPx())
                    )

                if (transformedMaleRect.contains(offset)) {
                    rects?.forEach { array ->
                        array.forEach { rect ->
                            if (rect.contains(offset)) {
                                Log.d("TEST_TEST", "TEST" + rect.center.x + ": " + rect.center.y)
                                results.add(Position(
                                    rect.center.x,
                                    rect.center.y,
                                    isOval = false
                                ))
                                return@detectTapGestures
                            }
                        }
                    }
                }
            }
        }
    ) {
        val halfSize = size.toPx() / 2
        val distanceBetweenLines = size.toPx() / 6
        val center = this.center
        val topLeftOffset = Offset(center.x - halfSize, y = center.y - halfSize)

        rectOffset = topLeftOffset

        if (rectOffset != Offset.Zero) {
            val oneThird = size.toPx() / 3
            rects = Array(3) { i ->
                Array(3) { j ->
                    Rect(
                        Offset(x = rectOffset.x + j * oneThird, y = rectOffset.y + i * oneThird),
                        Size(oneThird, oneThird)
                    )
                }
            }
        }

        drawRect(
            color = Color.Green,
            topLeft = topLeftOffset,
            size = Size(size.toPx(), size.toPx())
        )

        results.forEach {
            drawPath(
                Path().apply {
                    moveTo(it.x, it.y)
                    lineTo(it.x + 20, it.y + 20)
                },
                color = Color.Red,
                style = Stroke(
                    width = 5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        drawPath(
            Path().apply {
                moveTo(center.x - halfSize, center.y - distanceBetweenLines)
                lineTo(center.x + halfSize, center.y - distanceBetweenLines)
            },
            color = Color.Black,
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
        drawPath(
            Path().apply {
                moveTo(center.x - halfSize, center.y + distanceBetweenLines)
                lineTo(center.x + halfSize, center.y + distanceBetweenLines)
            },
            color = Color.Black,
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
        drawPath(
            Path().apply {
                moveTo(center.x - distanceBetweenLines, center.y - halfSize)
                lineTo(center.x - distanceBetweenLines, center.y + halfSize)
            },
            color = Color.Black,
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
        drawPath(
            Path().apply {
                moveTo(center.x + distanceBetweenLines, center.y - halfSize)
                lineTo(center.x + distanceBetweenLines, center.y + halfSize)
            },
            color = Color.Black,
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}