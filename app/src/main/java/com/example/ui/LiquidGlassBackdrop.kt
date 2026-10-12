package com.example.ui

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import com.example.ui.theme.CalcBlack
import com.example.ui.theme.CalcOrange
import com.example.ui.theme.CalcWhite

/**
 * Restrained geometric backdrop layer behind the keypad.
 * Large hard-edged geometric shapes in strictly the three allowed palette colors
 * (Black, Orange, White) with low alpha, drifting very slowly.
 * Strictly no glowing or soft blobs.
 *
 * Provides true backdrop blur via RenderEffect on Android 12+ (API 31+).
 */
@Composable
fun LiquidGlassBackdrop(
    modifier: Modifier = Modifier,
    blurBehind: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "backdrop_drift")

    // Slow drifting phase 1 (horizontal & vertical drift over 32 seconds)
    val driftPhaseA by infiniteTransition.animateFloat(
        initialValue = -40f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 32000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift_a"
    )

    // Slow drifting phase 2 (over 24 seconds)
    val driftPhaseB by infiniteTransition.animateFloat(
        initialValue = 35f,
        targetValue = -35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 24000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift_b"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CalcBlack)
    ) {
        // Sharp backdrop geometric shapes layer
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val w = size.width
            val h = size.height

            // Shape 1: Large hard-edged white rectangle rotated at upper-left
            drawRect(
                color = CalcWhite.copy(alpha = 0.05f),
                topLeft = Offset(w * 0.05f + driftPhaseA, h * 0.15f + driftPhaseB),
                size = Size(w * 0.55f, h * 0.28f)
            )

            // Shape 2: Large hard-edged orange rectangle at lower-right
            drawRect(
                color = CalcOrange.copy(alpha = 0.08f),
                topLeft = Offset(w * 0.40f + driftPhaseB, h * 0.48f + driftPhaseA),
                size = Size(w * 0.60f, h * 0.32f)
            )

            // Shape 3: Hard-edged sharp polygon (triangle) in white at mid-screen
            val trianglePath = Path().apply {
                moveTo(w * 0.15f + driftPhaseB, h * 0.55f)
                lineTo(w * 0.65f + driftPhaseA, h * 0.62f)
                lineTo(w * 0.30f + driftPhaseA, h * 0.85f + driftPhaseB)
                close()
            }
            drawPath(
                path = trianglePath,
                color = CalcWhite.copy(alpha = 0.04f)
            )

            // Shape 4: Hard-edged sharp diamond polygon in orange at top-right
            val diamondPath = Path().apply {
                val cx = w * 0.75f + driftPhaseA
                val cy = h * 0.22f + driftPhaseB
                val radius = w * 0.22f
                moveTo(cx, cy - radius)
                lineTo(cx + radius, cy)
                lineTo(cx, cy + radius)
                lineTo(cx - radius, cy)
                close()
            }
            drawPath(
                path = diamondPath,
                color = CalcOrange.copy(alpha = 0.06f)
            )

            // Shape 5: Crisp square at bottom-left in white
            drawRect(
                color = CalcWhite.copy(alpha = 0.04f),
                topLeft = Offset(w * 0.08f + driftPhaseB, h * 0.82f - driftPhaseA),
                size = Size(w * 0.42f, h * 0.20f)
            )
        }

        // Blurred backdrop reflection layer for liquid glass diffusion on API 31+
        if (blurBehind && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        renderEffect = RenderEffect
                            .createBlurEffect(60f, 60f, Shader.TileMode.CLAMP)
                            .asComposeRenderEffect()
                    }
            ) {
                val w = size.width
                val h = size.height

                // Same geometric shapes blurred to provide diffuse liquid glass field
                drawRect(
                    color = CalcWhite.copy(alpha = 0.06f),
                    topLeft = Offset(w * 0.05f + driftPhaseA, h * 0.15f + driftPhaseB),
                    size = Size(w * 0.55f, h * 0.28f)
                )
                drawRect(
                    color = CalcOrange.copy(alpha = 0.09f),
                    topLeft = Offset(w * 0.40f + driftPhaseB, h * 0.48f + driftPhaseA),
                    size = Size(w * 0.60f, h * 0.32f)
                )
            }
        }
    }
}
