package com.example.calc

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel(
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    private companion object {
        const val KEY_EXPRESSION = "key_expression"
        const val KEY_PAYWALL = "key_paywall"
        val OPERATORS = listOf("+", "−", "×", "÷")
    }

    private val _uiState = MutableStateFlow(
        CalculatorState(
            expression = savedStateHandle.get<String>(KEY_EXPRESSION) ?: "",
            isPaywallVisible = savedStateHandle.get<Boolean>(KEY_PAYWALL) ?: false
        )
    )
    val uiState: StateFlow<CalculatorState> = _uiState.asStateFlow()

    fun onDigit(digit: String) {
        _uiState.update { current ->
            val expr = current.expression
            val newExpr = when {
                expr == "0" -> digit
                expr.endsWith(" 0") -> expr.dropLast(1) + digit
                else -> expr + digit
            }
            savedStateHandle[KEY_EXPRESSION] = newExpr
            current.copy(expression = newExpr, activeOperator = null)
        }
    }

    fun onDecimal() {
        _uiState.update { current ->
            val expr = current.expression
            val lastToken = expr.split(" ").lastOrNull() ?: ""
            val newExpr = when {
                expr.isEmpty() -> "0."
                expr.endsWith(" ") -> expr + "0."
                lastToken.contains(".") -> expr // Already has decimal in current operand
                else -> expr + "."
            }
            savedStateHandle[KEY_EXPRESSION] = newExpr
            current.copy(expression = newExpr)
        }
    }

    fun onOperator(op: String) {
        _uiState.update { current ->
            val expr = current.expression.trim()
            val newExpr = when {
                expr.isEmpty() -> "0 $op "
                OPERATORS.any { expr.endsWith(it) } -> {
                    // Replace trailing operator
                    val base = expr.dropLast(1).trim()
                    "$base $op "
                }
                else -> "$expr $op "
            }
            savedStateHandle[KEY_EXPRESSION] = newExpr
            current.copy(expression = newExpr, activeOperator = op)
        }
    }

    fun onToggleSign() {
        _uiState.update { current ->
            val expr = current.expression.trim()
            if (expr.isEmpty()) return@update current

            val tokens = expr.split(" ").toMutableList()
            val lastIndex = tokens.lastIndex
            val last = tokens[lastIndex]

            // If last is an operator, do not toggle
            if (OPERATORS.contains(last)) return@update current

            tokens[lastIndex] = if (last.startsWith("-")) {
                last.substring(1)
            } else {
                "-$last"
            }
            val newExpr = tokens.joinToString(" ")
            savedStateHandle[KEY_EXPRESSION] = newExpr
            current.copy(expression = newExpr)
        }
    }

    fun onPercent() {
        _uiState.update { current ->
            val expr = current.expression.trim()
            if (expr.isEmpty()) return@update current
            if (expr.endsWith("%")) return@update current
            if (OPERATORS.any { expr.endsWith(it) }) return@update current

            val newExpr = "$expr%"
            savedStateHandle[KEY_EXPRESSION] = newExpr
            current.copy(expression = newExpr)
        }
    }

    fun onClear() {
        _uiState.update { current ->
            savedStateHandle[KEY_EXPRESSION] = ""
            current.copy(expression = "", activeOperator = null)
        }
    }

    fun onEquals() {
        _uiState.update { current ->
            if (current.isPaywallVisible) {
                // Prevent stacking multiple paywalls
                return@update current
            }
            val isCalc = CalculationClassifier.isCompleteCalculation(current.expression)
            if (isCalc) {
                savedStateHandle[KEY_PAYWALL] = true
                current.copy(isPaywallVisible = true)
            } else {
                current
            }
        }
    }

    fun onDismissPaywall() {
        _uiState.update { current ->
            savedStateHandle[KEY_PAYWALL] = false
            current.copy(isPaywallVisible = false)
        }
    }
}
