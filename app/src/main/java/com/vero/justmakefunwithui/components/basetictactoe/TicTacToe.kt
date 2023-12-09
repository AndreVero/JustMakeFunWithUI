package com.vero.justmakefunwithui.components.basetictactoe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class PlayerGameItem(
    val centerX: Float,
    val centerY: Float,
    val player: Player,
    val animatable: Animatable<Float, AnimationVector1D>
)

sealed class Player(val symbol: Symbol) {
    object Player1: Player(Symbol.Oval)
    object Player2: Player(Symbol.Cross)
}

sealed interface Symbol {
    object Oval : Symbol
    object Cross : Symbol

}

@Composable
fun TicTacToe(
    size: Dp,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    var rectOffset by remember { mutableStateOf(Offset.Zero) }
    var rects by remember { mutableStateOf<Array<Array<Rect>>?>(null) }
    val results = remember { mutableStateListOf<PlayerGameItem>() }

    var currentPlayer by remember { mutableStateOf<Player>(Player.Player1) }

    Canvas(modifier = modifier
        .pointerInput(true) {
            detectTapGestures { offset ->
                val gameRect =
                    Rect(
                        offset = rectOffset,
                        size = Size(size.toPx(), size.toPx())
                    )

                if (gameRect.contains(offset)) {
                    rects?.forEach { array ->
                        array.forEach { rect ->
                            if (rect.contains(offset)) {
                                val position = PlayerGameItem(
                                    centerX = rect.center.x,
                                    centerY = rect.center.y,
                                    player = currentPlayer,
                                    animatable = Animatable(0f)
                                )

                                results.add(position)

                                currentPlayer = if (currentPlayer == Player.Player1)
                                    Player.Player2
                                else Player.Player1

                                coroutineScope.launch {
                                    position.animatable.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(
                                            durationMillis = 1000
                                        )
                                    )
                                }
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

        results.forEach {
            if (it.player.symbol == Symbol.Cross) {
                val path1 = Path().apply {
                    moveTo(it.centerX - 50f, it.centerY - 50f)
                    lineTo(it.centerX + 50f, it.centerY + 50)
                }
                val path2 = Path().apply {
                    moveTo(it.centerX - 50f, it.centerY + 50f)
                    lineTo(it.centerX + 50f, it.centerY - 50f)
                }

                val outPath1 = Path()
                PathMeasure().apply {
                    setPath(path1, false)
                    getSegment(0f, it.animatable.value * length, outPath1, true)
                }
                val outPath2 = Path()
                PathMeasure().apply {
                    setPath(path2, false)
                    getSegment(0f, it.animatable.value * length, outPath2, true)
                }

                drawPath(
                    outPath1,
                    color = Color.Red,
                    style = Stroke(
                        width = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                drawPath(
                    outPath2,
                    color = Color.Red,
                    style = Stroke(
                        width = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            } else {
                val circle = Path().apply {
                    addOval(Rect(center = Offset(it.centerX, it.centerY), radius = 50f))
                }

                val outPath = Path()
                PathMeasure().apply {
                    setPath(circle, false)
                    getSegment(0f, it.animatable.value * length, outPath, true)
                }

                drawPath(
                    path = outPath,
                    color = Color.Green,
                    style = Stroke(
                        width = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
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