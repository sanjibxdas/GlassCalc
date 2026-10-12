package com.example.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animation.ButtonAnimationRecipe
import com.example.animation.ButtonAnimationTable
import com.example.ui.theme.CalcOrange
import com.example.ui.theme.CalcWhite

enum class ButtonVisualKind {
    NUMBER,
    UTILITY,
    OPERATOR
}

/**
 * Liquid glass calculator button with true spring physics,
 * unique per-button animation transform recipe, 1px specular edge,
 * and strict 3-color palette compliance (Black, Orange, White).
 */
@Composable
fun LiquidGlassButton(
    buttonId: String,
    label: String,
    visualKind: ButtonVisualKind,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    contentDescriptionText: String = label,
    onButtonClick: () -> Unit
) {
    val recipe: ButtonAnimationRecipe = remember(buttonId) {
        ButtonAnimationTable.getRecipe(buttonId)
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressProgress = remember { Animatable(0f) }

    LaunchedEffect(isPressed) {
        pressProgress.animateTo(
            targetValue = if (isPressed) 1f else 0f,
            animationSpec = spring(
                dampingRatio = recipe.dampingRatio,
                stiffness = recipe.stiffness
            )
        )
    }

    val progress = pressProgress.value

    // Dynamic scale, translation, rotation calculated from recipe
    val scaleX = 1f - (1f - recipe.scaleX) * progress
    val scaleY = 1f - (1f - recipe.scaleY) * progress
    val translateY = recipe.translateYDp * progress
    val rotationZ = recipe.rotationDeg * progress

    // Base background colors based strictly on palette
    val currentBgColor = when (visualKind) {
        ButtonVisualKind.OPERATOR -> {
            val alphaHighlight = (progress * recipe.alphaShift).coerceIn(0f, 0.4f)
            CalcOrange.copy(alpha = 1f - alphaHighlight * 0.3f)
        }
        ButtonVisualKind.UTILITY -> {
            val alpha = (0.22f + progress * recipe.alphaShift).coerceIn(0.22f, 0.70f)
            CalcWhite.copy(alpha = alpha)
        }
        ButtonVisualKind.NUMBER -> {
            val alpha = (0.12f + progress * recipe.alphaShift).coerceIn(0.12f, 0.55f)
            CalcWhite.copy(alpha = alpha)
        }
    }

    // 1px subtle specular edge on glass
    val borderAlpha = (0.20f + progress * recipe.specularShift).coerceIn(0.20f, 0.65f)
    val specularBorder = BorderStroke(1.dp, CalcWhite.copy(alpha = borderAlpha))

    Surface(
        modifier = modifier
            .testTag("btn_$buttonId")
            .graphicsLayer {
                this.scaleX = scaleX
                this.scaleY = scaleY
                this.translationY = translateY * density
                this.rotationZ = rotationZ
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClickLabel = contentDescriptionText,
                onClick = onButtonClick
            ),
        shape = shape,
        color = currentBgColor,
        border = specularBorder,
        shadowElevation = (recipe.depthShiftDp * (1f - progress)).dp.coerceAtLeast(0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = CalcWhite,
                fontSize = if (visualKind == ButtonVisualKind.UTILITY) 24.sp else 30.sp,
                fontWeight = if (visualKind == ButtonVisualKind.OPERATOR) FontWeight.Medium else FontWeight.Normal
            )
        }
    }
}
