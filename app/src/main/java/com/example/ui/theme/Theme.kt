package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Strict Black Theme with only the 3 allowed colors: Black, Orange (#FF9F0A), White (#FFFFFF)
private val CalculatorColorScheme = darkColorScheme(
    primary = CalcOrange,
    onPrimary = CalcWhite,
    primaryContainer = CalcOrange,
    onPrimaryContainer = CalcWhite,
    secondary = CalcWhite,
    onSecondary = CalcBlack,
    secondaryContainer = GlassWhiteMedium,
    onSecondaryContainer = CalcWhite,
    tertiary = CalcOrange,
    onTertiary = CalcWhite,
    background = CalcBlack,
    onBackground = CalcWhite,
    surface = CalcBlack,
    onSurface = CalcWhite,
    surfaceVariant = GlassWhiteSubtle,
    onSurfaceVariant = CalcWhite,
    outline = GlassSpecularBorder,
    outlineVariant = GlassWhiteFaint
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CalculatorColorScheme,
        typography = Typography,
        content = content
    )
}
