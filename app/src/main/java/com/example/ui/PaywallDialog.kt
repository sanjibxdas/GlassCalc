package com.example.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animation.ButtonAnimationRecipe
import com.example.animation.ButtonAnimationTable
import com.example.paywall.PaywallConfig
import com.example.ui.theme.CalcBlack
import com.example.ui.theme.CalcOrange
import com.example.ui.theme.CalcWhite

/**
 * Floating animated liquid glass paywall prank dialog.
 * Appears when '=' is pressed on a complete, valid calculation.
 * Never displays, logs, or computes mathematical results.
 */
@Composable
fun PaywallDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isVisible) return

    val context = LocalContext.current

    // Intercept system Back button to dismiss paywall
    BackHandler {
        onDismiss()
    }

    // Modal scrim over calculator
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CalcBlack.copy(alpha = 0.70f))
            .testTag("paywall_dialog_scrim")
            .pointerInput(Unit) {
                // Prevent clicks from falling through to the keypad
                detectTapGestures { /* consume */ }
            },
        contentAlignment = Alignment.Center
    ) {
        // Floating glass card
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 420.dp)
                .fillMaxWidth()
                .testTag("paywall_dialog"),
            shape = RoundedCornerShape(28.dp),
            color = CalcBlack.copy(alpha = 0.88f),
            border = BorderStroke(1.dp, CalcWhite.copy(alpha = 0.28f)),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top header row: small X close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    PaywallAnimatedButton(
                        recipe = ButtonAnimationTable.getRecipe("paywall_x"),
                        testTag = "btn_paywall_x",
                        contentDescriptionText = "Close paywall",
                        shape = CircleShape,
                        backgroundColor = CalcWhite.copy(alpha = 0.15f),
                        borderColor = CalcWhite.copy(alpha = 0.25f),
                        modifier = Modifier.size(36.dp),
                        onButtonClick = onDismiss
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = CalcWhite,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Mandatory Headline text: "Pay $500 to activate calculator for one week."
                Text(
                    text = PaywallConfig.HEADLINE,
                    color = CalcWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("paywall_headline")
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Highlighted Primary button: "Pay $500 to unlock it for one week."
                PaywallAnimatedButton(
                    recipe = ButtonAnimationTable.getRecipe("paywall_pay"),
                    testTag = "btn_paywall_pay",
                    contentDescriptionText = PaywallConfig.PRIMARY_BUTTON_LABEL,
                    shape = RoundedCornerShape(18.dp),
                    backgroundColor = CalcOrange,
                    borderColor = CalcWhite.copy(alpha = 0.35f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onButtonClick = {
                        launchWhatsApp(context)
                    }
                ) {
                    Text(
                        text = PaywallConfig.PRIMARY_BUTTON_LABEL,
                        color = CalcWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Secondary button: "I will think later."
                PaywallAnimatedButton(
                    recipe = ButtonAnimationTable.getRecipe("paywall_later"),
                    testTag = "btn_paywall_later",
                    contentDescriptionText = PaywallConfig.SECONDARY_BUTTON_LABEL,
                    shape = RoundedCornerShape(18.dp),
                    backgroundColor = CalcWhite.copy(alpha = 0.10f),
                    borderColor = CalcWhite.copy(alpha = 0.20f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onButtonClick = onDismiss
                ) {
                    Text(
                        text = PaywallConfig.SECONDARY_BUTTON_LABEL,
                        color = CalcWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Reusable animated button for paywall elements utilizing its distinct
 * [ButtonAnimationRecipe] spring physics and transform parameters.
 */
@Composable
private fun PaywallAnimatedButton(
    recipe: ButtonAnimationRecipe,
    testTag: String,
    contentDescriptionText: String,
    shape: androidx.compose.ui.graphics.Shape,
    backgroundColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressProgress = remember { androidx.compose.animation.core.Animatable(0f) }

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
    val scaleX = 1f - (1f - recipe.scaleX) * progress
    val scaleY = 1f - (1f - recipe.scaleY) * progress
    val translateY = recipe.translateYDp * progress
    val rotationZ = recipe.rotationDeg * progress

    Surface(
        modifier = modifier
            .testTag(testTag)
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
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

/**
 * Opens WhatsApp with an ACTION_VIEW intent on:
 * https://wa.me/<digits-only number>?text=<URL-encoded message>
 * Handles WhatsApp not being installed by falling back to browser or displaying a non-blocking toast.
 */
private fun launchWhatsApp(context: Context) {
    val uri = Uri.parse(PaywallConfig.buildWhatsAppUri())
    val intent = Intent(Intent.ACTION_VIEW, uri)

    try {
        // Try directing to WhatsApp application first
        intent.setPackage("com.whatsapp")
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        try {
            // Fall back to browser / system default handler
            val fallbackIntent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(fallbackIntent)
        } catch (e2: Exception) {
            // Non-blocking notification if no browser or handler available
            Toast.makeText(context, "WhatsApp is not installed on this device.", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        try {
            val fallbackIntent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(fallbackIntent)
        } catch (e2: Exception) {
            Toast.makeText(context, "Unable to open link.", Toast.LENGTH_SHORT).show()
        }
    }
}
