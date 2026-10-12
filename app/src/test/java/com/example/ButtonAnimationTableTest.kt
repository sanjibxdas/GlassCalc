package com.example

import com.example.animation.ButtonAnimationRecipe
import com.example.animation.ButtonAnimationTable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ButtonAnimationTableTest {

    private val expectedButtonIds = listOf(
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        ".", "AC", "+/-", "%", "÷", "×", "−", "+", "=",
        "paywall_x", "paywall_later", "paywall_pay"
    )

    @Test
    fun testAllExpectedButtonsArePresent() {
        assertEquals("Table must contain exactly 22 button recipes", 22, ButtonAnimationTable.RECIPES.size)
        for (id in expectedButtonIds) {
            assertTrue("Recipe table must contain button id: $id", ButtonAnimationTable.RECIPES.containsKey(id))
        }
    }

    @Test
    fun testEveryRecipeIsStrictlyUnique() {
        val recipes = ButtonAnimationTable.RECIPES.values.toList()

        for (i in 0 until recipes.size) {
            for (j in i + 1 until recipes.size) {
                val a = recipes[i]
                val b = recipes[j]

                val isIdentical = (a.dampingRatio == b.dampingRatio &&
                        a.stiffness == b.stiffness &&
                        a.scaleX == b.scaleX &&
                        a.scaleY == b.scaleY &&
                        a.translateYDp == b.translateYDp &&
                        a.rotationDeg == b.rotationDeg &&
                        a.alphaShift == b.alphaShift &&
                        a.specularShift == b.specularShift &&
                        a.depthShiftDp == b.depthShiftDp)

                assertNotEquals(
                    "Recipes for '${a.id}' and '${b.id}' must NOT be identical",
                    true,
                    isIdentical
                )
            }
        }
    }

    @Test
    fun testSpringParametersWithinIOSFluidRange() {
        for ((id, recipe) in ButtonAnimationTable.RECIPES) {
            assertTrue(
                "Button '$id' damping ratio (${recipe.dampingRatio}) must be >= 0.60",
                recipe.dampingRatio >= 0.60f
            )
            assertTrue(
                "Button '$id' damping ratio (${recipe.dampingRatio}) must be <= 0.85",
                recipe.dampingRatio <= 0.85f
            )
            assertTrue(
                "Button '$id' stiffness must be positive and fluid",
                recipe.stiffness in 300f..1200f
            )
        }
    }
}
