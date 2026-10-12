package com.example

import android.app.Application
import android.content.Intent
import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.calc.CalculatorViewModel
import com.example.paywall.PaywallConfig
import com.example.ui.CalculatorScreen
import com.example.ui.theme.MyApplicationTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CalculatorRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEnteringTwoTimesTwoThenEqualsShowsPaywallWithNoResult() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            MyApplicationTheme {
                val state = viewModel.uiState.value
                CalculatorScreen(
                    state = state,
                    onDigit = viewModel::onDigit,
                    onDecimal = viewModel::onDecimal,
                    onOperator = viewModel::onOperator,
                    onToggleSign = viewModel::onToggleSign,
                    onPercent = viewModel::onPercent,
                    onClear = viewModel::onClear,
                    onEquals = viewModel::onEquals,
                    onDismissPaywall = viewModel::onDismissPaywall
                )
            }
        }

        // Enter 2 × 2
        composeTestRule.onNodeWithTag("btn_2").performClick()
        composeTestRule.onNodeWithTag("btn_×").performClick()
        composeTestRule.onNodeWithTag("btn_2").performClick()

        // Press =
        composeTestRule.onNodeWithTag("btn_=").performClick()

        // Verify paywall headline is displayed with exact text
        composeTestRule.onNodeWithTag("paywall_headline")
            .assertIsDisplayed()
            .assertTextEquals("Pay $500 to activate calculator for one week.")

        // CRITICAL: Ensure mathematical result '4' does not appear anywhere in the semantics tree
        composeTestRule.onNodeWithText("4").assertDoesNotExist()
    }

    @Test
    fun testLoneNumberThenEqualsDoesNothing() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            MyApplicationTheme {
                val state = viewModel.uiState.value
                CalculatorScreen(
                    state = state,
                    onDigit = viewModel::onDigit,
                    onDecimal = viewModel::onDecimal,
                    onOperator = viewModel::onOperator,
                    onToggleSign = viewModel::onToggleSign,
                    onPercent = viewModel::onPercent,
                    onClear = viewModel::onClear,
                    onEquals = viewModel::onEquals,
                    onDismissPaywall = viewModel::onDismissPaywall
                )
            }
        }

        // Enter lone number 2 then =
        composeTestRule.onNodeWithTag("btn_2").performClick()
        composeTestRule.onNodeWithTag("btn_=").performClick()

        // Paywall must not be displayed
        composeTestRule.onNodeWithTag("paywall_dialog").assertDoesNotExist()
    }

    @Test
    fun testPaywallDismissWithXAndReappearOnEquals() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            MyApplicationTheme {
                val state = viewModel.uiState.value
                CalculatorScreen(
                    state = state,
                    onDigit = viewModel::onDigit,
                    onDecimal = viewModel::onDecimal,
                    onOperator = viewModel::onOperator,
                    onToggleSign = viewModel::onToggleSign,
                    onPercent = viewModel::onPercent,
                    onClear = viewModel::onClear,
                    onEquals = viewModel::onEquals,
                    onDismissPaywall = viewModel::onDismissPaywall
                )
            }
        }

        // Enter 9 − 3 then =
        composeTestRule.onNodeWithTag("btn_9").performClick()
        composeTestRule.onNodeWithTag("btn_−").performClick()
        composeTestRule.onNodeWithTag("btn_3").performClick()
        composeTestRule.onNodeWithTag("btn_=").performClick()

        // Paywall appears
        composeTestRule.onNodeWithTag("paywall_dialog").assertIsDisplayed()

        // Dismiss with X
        composeTestRule.onNodeWithTag("btn_paywall_x").performClick()
        composeTestRule.onNodeWithTag("paywall_dialog").assertDoesNotExist()

        // Press = again on same expression brings paywall back
        composeTestRule.onNodeWithTag("btn_=").performClick()
        composeTestRule.onNodeWithTag("paywall_dialog").assertIsDisplayed()
    }

    @Test
    fun testPaywallDismissWithThinkLater() {
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            MyApplicationTheme {
                val state = viewModel.uiState.value
                CalculatorScreen(
                    state = state,
                    onDigit = viewModel::onDigit,
                    onDecimal = viewModel::onDecimal,
                    onOperator = viewModel::onOperator,
                    onToggleSign = viewModel::onToggleSign,
                    onPercent = viewModel::onPercent,
                    onClear = viewModel::onClear,
                    onEquals = viewModel::onEquals,
                    onDismissPaywall = viewModel::onDismissPaywall
                )
            }
        }

        // Enter 8 ÷ 4 then =
        composeTestRule.onNodeWithTag("btn_8").performClick()
        composeTestRule.onNodeWithTag("btn_÷").performClick()
        composeTestRule.onNodeWithTag("btn_4").performClick()
        composeTestRule.onNodeWithTag("btn_=").performClick()

        composeTestRule.onNodeWithTag("paywall_dialog").assertIsDisplayed()

        // Dismiss with 'I will think later.'
        composeTestRule.onNodeWithTag("btn_paywall_later").performClick()
        composeTestRule.onNodeWithTag("paywall_dialog").assertDoesNotExist()
    }

    @Test
    fun testPrimaryButtonFiresWhatsAppIntentWithExactUri() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CalculatorViewModel()

        composeTestRule.setContent {
            MyApplicationTheme {
                val state = viewModel.uiState.value
                CalculatorScreen(
                    state = state,
                    onDigit = viewModel::onDigit,
                    onDecimal = viewModel::onDecimal,
                    onOperator = viewModel::onOperator,
                    onToggleSign = viewModel::onToggleSign,
                    onPercent = viewModel::onPercent,
                    onClear = viewModel::onClear,
                    onEquals = viewModel::onEquals,
                    onDismissPaywall = viewModel::onDismissPaywall
                )
            }
        }

        // Trigger paywall
        composeTestRule.onNodeWithTag("btn_2").performClick()
        composeTestRule.onNodeWithTag("btn_+").performClick()
        composeTestRule.onNodeWithTag("btn_2").performClick()
        composeTestRule.onNodeWithTag("btn_=").performClick()

        composeTestRule.onNodeWithTag("btn_paywall_pay").performClick()

        // Verify intent fired
        val nextIntent = Shadows.shadowOf(app).nextStartedActivity
        assertNotNull("An intent must be fired when primary button is clicked", nextIntent)
        assertEquals(Intent.ACTION_VIEW, nextIntent.action)
        assertEquals(PaywallConfig.buildWhatsAppUri(), nextIntent.dataString)
    }
}
