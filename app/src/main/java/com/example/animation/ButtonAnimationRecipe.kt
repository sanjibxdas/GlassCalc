package com.example.animation

/**
 * Transform and spring physics parameters for a specific button.
 * Defines iOS-style fluid spring response:
 * - [dampingRatio]: Spring damping ratio (between 0.60 and 0.85)
 * - [stiffness]: Spring stiffness constant
 * - [scaleX]: Horizontal scale factor when fully pressed
 * - [scaleY]: Vertical scale factor when fully pressed (squish/stretch)
 * - [translateYDp]: Downward translation in dp on press
 * - [rotationDeg]: Subtle angular tilt in degrees on press
 * - [alphaShift]: Glass highlight tint increase on press
 * - [specularShift]: Specular highlight width/intensity increase on press
 * - [depthShiftDp]: Elevation/depth shift in dp on press
 */
data class ButtonAnimationRecipe(
    val id: String,
    val dampingRatio: Float,
    val stiffness: Float,
    val scaleX: Float,
    val scaleY: Float,
    val translateYDp: Float,
    val rotationDeg: Float,
    val alphaShift: Float,
    val specularShift: Float,
    val depthShiftDp: Float
)
