package com.example.calc

/**
 * Immutable state for the liquid glass calculator.
 * Strictly guarantees that no mathematical results are ever computed or stored.
 */
data class CalculatorState(
    val expression: String = "",
    val isPaywallVisible: Boolean = false,
    val activeOperator: String? = null
)
