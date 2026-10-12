package com.example

import com.example.calc.CalculationClassifier
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculationClassifierTest {

    @Test
    fun testValidCalculations() {
        assertTrue("2 × 2 should be valid calculation", CalculationClassifier.isCompleteCalculation("2 × 2"))
        assertTrue("9 − 3 should be valid calculation", CalculationClassifier.isCompleteCalculation("9 − 3"))
        assertTrue("8 ÷ 4 should be valid calculation", CalculationClassifier.isCompleteCalculation("8 ÷ 4"))
        assertTrue("10 + 5 should be valid calculation", CalculationClassifier.isCompleteCalculation("10 + 5"))
    }

    @Test
    fun testEmptyExpression() {
        assertFalse("Empty string is not a calculation", CalculationClassifier.isCompleteCalculation(""))
        assertFalse("Whitespace is not a calculation", CalculationClassifier.isCompleteCalculation("   "))
    }

    @Test
    fun testLoneNumbers() {
        assertFalse("Lone digit 2 is not a calculation", CalculationClassifier.isCompleteCalculation("2"))
        assertFalse("Lone integer 42 is not a calculation", CalculationClassifier.isCompleteCalculation("42"))
        assertFalse("Lone negative number -5 is not a calculation", CalculationClassifier.isCompleteCalculation("-5"))
        assertFalse("Lone decimal 3.14 is not a calculation", CalculationClassifier.isCompleteCalculation("3.14"))
        assertFalse("Lone decimal .5 is not a calculation", CalculationClassifier.isCompleteCalculation(".5"))
    }

    @Test
    fun testIncompleteExpressions() {
        assertFalse("Trailing + is incomplete", CalculationClassifier.isCompleteCalculation("5 +"))
        assertFalse("Trailing × is incomplete", CalculationClassifier.isCompleteCalculation("2 ×"))
        assertFalse("Trailing ÷ is incomplete", CalculationClassifier.isCompleteCalculation("8 ÷"))
        assertFalse("Leading ÷ without operand is incomplete", CalculationClassifier.isCompleteCalculation("÷ 3"))
        assertFalse("Leading + without operand is incomplete", CalculationClassifier.isCompleteCalculation("+ 8"))
    }

    @Test
    fun testDivideByZero() {
        assertTrue("8 ÷ 0 counts as a complete calculation", CalculationClassifier.isCompleteCalculation("8 ÷ 0"))
        assertTrue("100 / 0 counts as a complete calculation", CalculationClassifier.isCompleteCalculation("100 / 0"))
    }

    @Test
    fun testChainedOperators() {
        assertTrue("2 + 3 × 4 is a complete calculation", CalculationClassifier.isCompleteCalculation("2 + 3 × 4"))
        assertTrue("10 − 4 + 2 is a complete calculation", CalculationClassifier.isCompleteCalculation("10 − 4 + 2"))
    }

    @Test
    fun testDecimals() {
        assertTrue("2.5 × 4 is a complete calculation", CalculationClassifier.isCompleteCalculation("2.5 × 4"))
        assertTrue("0.1 + .5 is a complete calculation", CalculationClassifier.isCompleteCalculation("0.1 + .5"))
    }

    @Test
    fun testPercentage() {
        assertTrue("50% counts as a calculation with operator %", CalculationClassifier.isCompleteCalculation("50%"))
        assertTrue("100 + 10% is a complete calculation", CalculationClassifier.isCompleteCalculation("100 + 10%"))
    }

    @Test
    fun testInvalidSequences() {
        assertFalse("Adjacent binary operators are invalid", CalculationClassifier.isCompleteCalculation("5 + + 3"))
    }
}
