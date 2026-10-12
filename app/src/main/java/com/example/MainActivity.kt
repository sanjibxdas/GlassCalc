package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calc.CalculatorViewModel
import com.example.ui.CalculatorScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                CalculatorScreen(
                    state = state,
                    onDigit = viewModel::onDigit,
                    onDecimal = viewModel::onDecimal,
                    onOperator = viewModel::onOperator,
                    onToggleSign = viewModel::onToggleSign,
                    onPercent = viewModel::onPercent,
                    onClear = viewModel::onClear,
                    onEquals = viewModel::onEquals,
                    onDismissPaywall = viewModel::onDismissPaywall,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
