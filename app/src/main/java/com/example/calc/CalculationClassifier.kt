package com.example.calc

/**
 * Pure Kotlin expression classifier that determines whether a given expression string
 * is an "actual calculation" (a complete, valid expression with all operands present
 * and at least one operator +, −, ×, ÷, %).
 *
 * CRITICAL CONSTRAINT:
 * This class NEVER evaluates, computes, or stores the mathematical result.
 * It strictly performs lexical and grammatical syntactic classification.
 */
object CalculationClassifier {

    private val BINARY_OPERATORS = setOf('+', '−', '-', '×', '*', '÷', '/')

    /**
     * Returns true if the expression represents a complete, valid calculation.
     * Division by zero counts as a valid calculation.
     * Lone numbers, empty strings, and trailing operators return false.
     */
    fun isCompleteCalculation(rawExpression: String): Boolean {
        val expr = rawExpression.trim()
        if (expr.isEmpty()) return false

        // Normalize spaces around operators for tokenization
        // Keep track of tokens: numbers (with optional sign/decimals), operators, and percentage
        val tokens = tokenize(expr) ?: return false
        if (tokens.isEmpty()) return false

        // Must have at least one operator (+, −, ×, ÷, %)
        val hasOperator = tokens.any { it is Token.Op || it is Token.Percent }
        if (!hasOperator) return false

        // Validate grammar:
        // Expression grammar:
        // [SignedNumber] ([Op] [SignedNumber] | [Percent])+
        // Cannot end in a binary operator.
        // Percentage can follow a number or another percentage.
        // Division by zero is allowed (syntax is valid).
        var expectOperand = true
        var operandCount = 0
        var operatorCount = 0

        for (i in tokens.indices) {
            when (val token = tokens[i]) {
                is Token.Num -> {
                    if (!expectOperand) return false
                    operandCount++
                    expectOperand = false
                }
                is Token.Op -> {
                    if (expectOperand) return false
                    operatorCount++
                    expectOperand = true
                }
                is Token.Percent -> {
                    // Percent modifies the preceding operand, so expectOperand must be false
                    if (expectOperand) return false
                    operatorCount++
                    // After percent, next can be an operator or end of expression
                    expectOperand = false
                }
            }
        }

        // Must not be expecting another operand at the end
        if (expectOperand) return false

        // A valid calculation has at least 1 operand and at least 1 operator/percent
        return operandCount >= 1 && operatorCount >= 1
    }

    private sealed interface Token {
        data class Num(val text: String) : Token
        data class Op(val char: Char) : Token
        data object Percent : Token
    }

    private fun tokenize(text: String): List<Token>? {
        val result = mutableListOf<Token>()
        var index = 0
        val n = text.length

        while (index < n) {
            val c = text[index]

            if (c.isWhitespace()) {
                index++
                continue
            }

            if (c == '%') {
                result.add(Token.Percent)
                index++
                continue
            }

            // Check for binary operator or unary minus
            if (c in BINARY_OPERATORS) {
                // If it's a minus sign, check whether it's a negative sign for the next number:
                // It is unary negative if it's at start OR immediately after a binary operator
                val isMinus = (c == '−' || c == '-')
                val prevIsOpOrStart = result.isEmpty() || result.last() is Token.Op

                if (isMinus && prevIsOpOrStart) {
                    // Parse negative number
                    val numStart = index
                    index++ // consume '-'
                    val digitStart = index
                    while (index < n && (text[index].isDigit() || text[index] == '.')) {
                        index++
                    }
                    if (index == digitStart) {
                        // Minus with no digits immediately following
                        return null
                    }
                    val numStr = text.substring(numStart, index)
                    if (!isValidNumber(numStr)) return null
                    result.add(Token.Num(numStr))
                    continue
                } else {
                    // Binary operator
                    result.add(Token.Op(c))
                    index++
                    continue
                }
            }

            if (c.isDigit() || c == '.') {
                val start = index
                while (index < n && (text[index].isDigit() || text[index] == '.')) {
                    index++
                }
                val numStr = text.substring(start, index)
                if (!isValidNumber(numStr)) return null
                result.add(Token.Num(numStr))
                continue
            }

            // Unknown character
            return null
        }

        return result
    }

    private fun isValidNumber(str: String): Boolean {
        if (str.isEmpty()) return false
        val s = if (str.startsWith("-") || str.startsWith("−")) str.substring(1) else str
        if (s.isEmpty()) return false
        val dotCount = s.count { it == '.' }
        if (dotCount > 1) return false
        // Must have at least one digit
        return s.any { it.isDigit() }
    }
}
