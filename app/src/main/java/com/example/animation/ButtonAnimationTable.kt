package com.example.animation

/**
 * Predefined master animation table containing distinct recipes for all 22 buttons:
 * 0-9 (10 number keys), . (decimal), AC, +/-, %, ÷, ×, −, +, = (8 operator/utility keys),
 * plus paywall X, paywall "I will think later", and paywall primary pay button.
 *
 * Guaranteed by unit test: no two recipes are identical.
 */
object ButtonAnimationTable {

    val RECIPES: Map<String, ButtonAnimationRecipe> = mapOf(
        "0" to ButtonAnimationRecipe(
            id = "0",
            dampingRatio = 0.82f,
            stiffness = 520f,
            scaleX = 0.97f,
            scaleY = 0.92f,
            translateYDp = 1.8f,
            rotationDeg = 0.0f,
            alphaShift = 0.22f,
            specularShift = 0.18f,
            depthShiftDp = 2.0f
        ),
        "1" to ButtonAnimationRecipe(
            id = "1",
            dampingRatio = 0.78f,
            stiffness = 600f,
            scaleX = 0.93f,
            scaleY = 0.95f,
            translateYDp = 2.1f,
            rotationDeg = -0.4f,
            alphaShift = 0.20f,
            specularShift = 0.20f,
            depthShiftDp = 2.2f
        ),
        "2" to ButtonAnimationRecipe(
            id = "2",
            dampingRatio = 0.77f,
            stiffness = 610f,
            scaleX = 0.94f,
            scaleY = 0.94f,
            translateYDp = 2.0f,
            rotationDeg = 0.0f,
            alphaShift = 0.21f,
            specularShift = 0.22f,
            depthShiftDp = 2.1f
        ),
        "3" to ButtonAnimationRecipe(
            id = "3",
            dampingRatio = 0.76f,
            stiffness = 620f,
            scaleX = 0.93f,
            scaleY = 0.95f,
            translateYDp = 2.1f,
            rotationDeg = 0.4f,
            alphaShift = 0.20f,
            specularShift = 0.24f,
            depthShiftDp = 2.2f
        ),
        "4" to ButtonAnimationRecipe(
            id = "4",
            dampingRatio = 0.75f,
            stiffness = 640f,
            scaleX = 0.92f,
            scaleY = 0.96f,
            translateYDp = 2.3f,
            rotationDeg = -0.5f,
            alphaShift = 0.23f,
            specularShift = 0.21f,
            depthShiftDp = 2.3f
        ),
        "5" to ButtonAnimationRecipe(
            id = "5",
            dampingRatio = 0.74f,
            stiffness = 650f,
            scaleX = 0.93f,
            scaleY = 0.93f,
            translateYDp = 2.2f,
            rotationDeg = 0.0f,
            alphaShift = 0.24f,
            specularShift = 0.23f,
            depthShiftDp = 2.2f
        ),
        "6" to ButtonAnimationRecipe(
            id = "6",
            dampingRatio = 0.73f,
            stiffness = 660f,
            scaleX = 0.92f,
            scaleY = 0.96f,
            translateYDp = 2.3f,
            rotationDeg = 0.5f,
            alphaShift = 0.23f,
            specularShift = 0.25f,
            depthShiftDp = 2.3f
        ),
        "7" to ButtonAnimationRecipe(
            id = "7",
            dampingRatio = 0.72f,
            stiffness = 680f,
            scaleX = 0.91f,
            scaleY = 0.97f,
            translateYDp = 2.5f,
            rotationDeg = -0.6f,
            alphaShift = 0.25f,
            specularShift = 0.22f,
            depthShiftDp = 2.4f
        ),
        "8" to ButtonAnimationRecipe(
            id = "8",
            dampingRatio = 0.71f,
            stiffness = 690f,
            scaleX = 0.92f,
            scaleY = 0.92f,
            translateYDp = 2.4f,
            rotationDeg = 0.0f,
            alphaShift = 0.26f,
            specularShift = 0.24f,
            depthShiftDp = 2.4f
        ),
        "9" to ButtonAnimationRecipe(
            id = "9",
            dampingRatio = 0.70f,
            stiffness = 700f,
            scaleX = 0.91f,
            scaleY = 0.97f,
            translateYDp = 2.5f,
            rotationDeg = 0.6f,
            alphaShift = 0.25f,
            specularShift = 0.26f,
            depthShiftDp = 2.4f
        ),
        "." to ButtonAnimationRecipe(
            id = ".",
            dampingRatio = 0.84f,
            stiffness = 540f,
            scaleX = 0.95f,
            scaleY = 0.95f,
            translateYDp = 1.5f,
            rotationDeg = 0.3f,
            alphaShift = 0.18f,
            specularShift = 0.19f,
            depthShiftDp = 1.8f
        ),
        "AC" to ButtonAnimationRecipe(
            id = "AC",
            dampingRatio = 0.68f,
            stiffness = 450f,
            scaleX = 0.90f,
            scaleY = 0.94f,
            translateYDp = 3.0f,
            rotationDeg = -0.8f,
            alphaShift = 0.35f,
            specularShift = 0.32f,
            depthShiftDp = 3.0f
        ),
        "+/-" to ButtonAnimationRecipe(
            id = "+/-",
            dampingRatio = 0.69f,
            stiffness = 480f,
            scaleX = 0.92f,
            scaleY = 0.94f,
            translateYDp = 2.8f,
            rotationDeg = 0.7f,
            alphaShift = 0.32f,
            specularShift = 0.30f,
            depthShiftDp = 2.8f
        ),
        "%" to ButtonAnimationRecipe(
            id = "%",
            dampingRatio = 0.70f,
            stiffness = 500f,
            scaleX = 0.93f,
            scaleY = 0.93f,
            translateYDp = 2.7f,
            rotationDeg = -0.4f,
            alphaShift = 0.30f,
            specularShift = 0.28f,
            depthShiftDp = 2.7f
        ),
        "÷" to ButtonAnimationRecipe(
            id = "÷",
            dampingRatio = 0.65f,
            stiffness = 750f,
            scaleX = 0.89f,
            scaleY = 0.95f,
            translateYDp = 3.2f,
            rotationDeg = 0.0f,
            alphaShift = 0.36f,
            specularShift = 0.34f,
            depthShiftDp = 3.2f
        ),
        "×" to ButtonAnimationRecipe(
            id = "×",
            dampingRatio = 0.64f,
            stiffness = 760f,
            scaleX = 0.90f,
            scaleY = 0.94f,
            translateYDp = 3.3f,
            rotationDeg = 0.8f,
            alphaShift = 0.37f,
            specularShift = 0.35f,
            depthShiftDp = 3.3f
        ),
        "−" to ButtonAnimationRecipe(
            id = "−",
            dampingRatio = 0.63f,
            stiffness = 770f,
            scaleX = 0.94f,
            scaleY = 0.90f,
            translateYDp = 3.1f,
            rotationDeg = 0.0f,
            alphaShift = 0.38f,
            specularShift = 0.36f,
            depthShiftDp = 3.1f
        ),
        "+" to ButtonAnimationRecipe(
            id = "+",
            dampingRatio = 0.62f,
            stiffness = 780f,
            scaleX = 0.91f,
            scaleY = 0.93f,
            translateYDp = 3.4f,
            rotationDeg = -0.7f,
            alphaShift = 0.39f,
            specularShift = 0.37f,
            depthShiftDp = 3.4f
        ),
        "=" to ButtonAnimationRecipe(
            id = "=",
            dampingRatio = 0.60f,
            stiffness = 800f,
            scaleX = 0.88f,
            scaleY = 0.92f,
            translateYDp = 3.5f,
            rotationDeg = 0.0f,
            alphaShift = 0.40f,
            specularShift = 0.40f,
            depthShiftDp = 3.5f
        ),
        "paywall_x" to ButtonAnimationRecipe(
            id = "paywall_x",
            dampingRatio = 0.80f,
            stiffness = 850f,
            scaleX = 0.86f,
            scaleY = 0.86f,
            translateYDp = 1.2f,
            rotationDeg = 5.0f,
            alphaShift = 0.28f,
            specularShift = 0.20f,
            depthShiftDp = 1.5f
        ),
        "paywall_later" to ButtonAnimationRecipe(
            id = "paywall_later",
            dampingRatio = 0.79f,
            stiffness = 580f,
            scaleX = 0.96f,
            scaleY = 0.94f,
            translateYDp = 2.0f,
            rotationDeg = -0.3f,
            alphaShift = 0.25f,
            specularShift = 0.22f,
            depthShiftDp = 2.0f
        ),
        "paywall_pay" to ButtonAnimationRecipe(
            id = "paywall_pay",
            dampingRatio = 0.66f,
            stiffness = 720f,
            scaleX = 0.94f,
            scaleY = 0.91f,
            translateYDp = 3.6f,
            rotationDeg = 0.0f,
            alphaShift = 0.42f,
            specularShift = 0.45f,
            depthShiftDp = 3.8f
        )
    )

    fun getRecipe(id: String): ButtonAnimationRecipe {
        return RECIPES[id] ?: ButtonAnimationRecipe(
            id = id,
            dampingRatio = 0.75f,
            stiffness = 600f,
            scaleX = 0.93f,
            scaleY = 0.93f,
            translateYDp = 2.0f,
            rotationDeg = 0.0f,
            alphaShift = 0.25f,
            specularShift = 0.25f,
            depthShiftDp = 2.0f
        )
    }
}
