package com.example.ui

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calc.CalculatorState
import com.example.ui.theme.CalcBlack
import com.example.ui.theme.CalcWhite

/**
 * Main liquid glass calculator screen adhering strictly to iOS layout proportions,
 * 4-column grid, fluid spring entrance animation, and floating paywall prank.
 */
@Composable
fun CalculatorScreen(
    state: CalculatorState,
    onDigit: (String) -> Unit,
    onDecimal: () -> Unit,
    onOperator: (String) -> Unit,
    onToggleSign: () -> Unit,
    onPercent: () -> Unit,
    onClear: () -> Unit,
    onEquals: () -> Unit,
    onDismissPaywall: () -> Unit,
    modifier: Modifier = Modifier
) {
    // App entrance animation: fluid spring scale & alpha
    val entranceAlpha = remember { Animatable(0f) }
    val entranceScale = remember { Animatable(0.92f) }

    LaunchedEffect(Unit) {
        entranceAlpha.animateTo(
            targetValue = 1f,
            animationSpec = spring(dampingRatio = 0.80f, stiffness = Spring.StiffnessMediumLow)
        )
    }
    LaunchedEffect(Unit) {
        entranceScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(dampingRatio = 0.72f, stiffness = Spring.StiffnessMediumLow)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CalcBlack)
    ) {
        // Geometric backdrop layer
        LiquidGlassBackdrop(modifier = Modifier.fillMaxSize())

        // Calculator container with entrance animation
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .graphicsLayer {
                    alpha = entranceAlpha.value
                    scaleX = entranceScale.value
                    scaleY = entranceScale.value
                    // When paywall is visible, blur the entire underlying calculator on API 31+
                    if (state.isPaywallVisible && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        renderEffect = RenderEffect
                            .createBlurEffect(25f, 25f, Shader.TileMode.CLAMP)
                            .asComposeRenderEffect()
                    }
                }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                // Top Display: shows the typed expression only
                val displayText = if (state.expression.isEmpty()) "0" else state.expression

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AnimatedContent(
                        targetState = displayText,
                        transitionSpec = {
                            fadeIn(animationSpec = spring(dampingRatio = 0.75f, stiffness = Spring.StiffnessMedium))
                                .togetherWith(fadeOut(animationSpec = tween(durationMillis = 100)))
                        },
                        label = "display_text_animation"
                    ) { targetText ->
                        val fontSize = when {
                            targetText.length > 14 -> 34.sp
                            targetText.length > 9 -> 46.sp
                            targetText.length > 6 -> 58.sp
                            else -> 72.sp
                        }

                        Text(
                            text = targetText,
                            color = CalcWhite,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("calculator_display")
                                .semantics {
                                    contentDescription = "Expression: $targetText"
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Keypad: 5 rows with 4-column grid (spacing 12.dp)
                val spacing = 12.dp

                // Row 1: AC, +/-, %, ÷
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing / 2),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    LiquidGlassButton(
                        buttonId = "AC",
                        label = "AC",
                        visualKind = ButtonVisualKind.UTILITY,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = onClear
                    )
                    LiquidGlassButton(
                        buttonId = "+/-",
                        label = "+/-",
                        visualKind = ButtonVisualKind.UTILITY,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = onToggleSign
                    )
                    LiquidGlassButton(
                        buttonId = "%",
                        label = "%",
                        visualKind = ButtonVisualKind.UTILITY,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = onPercent
                    )
                    LiquidGlassButton(
                        buttonId = "÷",
                        label = "÷",
                        visualKind = ButtonVisualKind.OPERATOR,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onOperator("÷") }
                    )
                }

                // Row 2: 7, 8, 9, ×
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing / 2),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    LiquidGlassButton(
                        buttonId = "7",
                        label = "7",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("7") }
                    )
                    LiquidGlassButton(
                        buttonId = "8",
                        label = "8",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("8") }
                    )
                    LiquidGlassButton(
                        buttonId = "9",
                        label = "9",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("9") }
                    )
                    LiquidGlassButton(
                        buttonId = "×",
                        label = "×",
                        visualKind = ButtonVisualKind.OPERATOR,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onOperator("×") }
                    )
                }

                // Row 3: 4, 5, 6, −
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing / 2),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    LiquidGlassButton(
                        buttonId = "4",
                        label = "4",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("4") }
                    )
                    LiquidGlassButton(
                        buttonId = "5",
                        label = "5",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("5") }
                    )
                    LiquidGlassButton(
                        buttonId = "6",
                        label = "6",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("6") }
                    )
                    LiquidGlassButton(
                        buttonId = "−",
                        label = "−",
                        visualKind = ButtonVisualKind.OPERATOR,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onOperator("−") }
                    )
                }

                // Row 4: 1, 2, 3, +
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing / 2),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    LiquidGlassButton(
                        buttonId = "1",
                        label = "1",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("1") }
                    )
                    LiquidGlassButton(
                        buttonId = "2",
                        label = "2",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("2") }
                    )
                    LiquidGlassButton(
                        buttonId = "3",
                        label = "3",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onDigit("3") }
                    )
                    LiquidGlassButton(
                        buttonId = "+",
                        label = "+",
                        visualKind = ButtonVisualKind.OPERATOR,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = { onOperator("+") }
                    )
                }

                // Row 5: 0 (pill spanning 2 columns), ., =
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing / 2),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    // '0' button spanning 2 columns with pill shape
                    LiquidGlassButton(
                        buttonId = "0",
                        label = "0",
                        visualKind = ButtonVisualKind.NUMBER,
                        shape = RoundedCornerShape(percent = 50),
                        modifier = Modifier
                            .weight(2f)
                            .aspectRatio(2.15f),
                        onButtonClick = { onDigit("0") }
                    )
                    LiquidGlassButton(
                        buttonId = ".",
                        label = ".",
                        visualKind = ButtonVisualKind.NUMBER,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = onDecimal
                    )
                    LiquidGlassButton(
                        buttonId = "=",
                        label = "=",
                        visualKind = ButtonVisualKind.OPERATOR,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onButtonClick = onEquals
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Floating Paywall Prank Overlay
        AnimatedVisibility(
            visible = state.isPaywallVisible,
            enter = fadeIn(animationSpec = spring(dampingRatio = 0.78f, stiffness = Spring.StiffnessMediumLow)) +
                    slideInVertically(
                        initialOffsetY = { it / 4 },
                        animationSpec = spring(dampingRatio = 0.72f, stiffness = Spring.StiffnessMediumLow)
                    ),
            exit = fadeOut(animationSpec = tween(durationMillis = 180)) +
                    slideOutVertically(
                        targetOffsetY = { it / 6 },
                        animationSpec = tween(durationMillis = 180)
                    )
        ) {
            PaywallDialog(
                isVisible = state.isPaywallVisible,
                onDismiss = onDismissPaywall
            )
        }
    }
}
