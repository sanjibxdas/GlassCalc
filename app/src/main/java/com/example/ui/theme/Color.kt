package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Three colors only across the entire application: Black, Orange, White.
val CalcBlack = Color(0xFF000000)
val CalcOrange = Color(0xFFFF9F0A)
val CalcWhite = Color(0xFFFFFFFF)

// Low-alpha glass tints and borders derived strictly from the three allowed hues
val GlassWhiteFaint = CalcWhite.copy(alpha = 0.08f)
val GlassWhiteSubtle = CalcWhite.copy(alpha = 0.12f)
val GlassWhiteMedium = CalcWhite.copy(alpha = 0.20f)
val GlassWhiteHighlight = CalcWhite.copy(alpha = 0.30f)
val GlassSpecularBorder = CalcWhite.copy(alpha = 0.22f)
val GlassSpecularPressed = CalcWhite.copy(alpha = 0.45f)
val GlassOrangeFaint = CalcOrange.copy(alpha = 0.15f)
