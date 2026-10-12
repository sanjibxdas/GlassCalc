package com.example.paywall

import java.net.URLEncoder

/**
 * Single source of truth configuration for the paywall prank.
 * All pricing texts, buttons, and WhatsApp intents derive strictly from [PRICE_AMOUNT]
 * and [DURATION_TEXT] constants.
 */
object PaywallConfig {
    const val PRICE_AMOUNT: Int = 500
    const val PRICE_CURRENCY: String = "$"
    val PRICE_DISPLAY: String = "$PRICE_CURRENCY$PRICE_AMOUNT"
    const val DURATION_TEXT: String = "one week"

    // Raw 9-digit WhatsApp recipient phone number without country code (as specified)
    const val WHATSAPP_PHONE_NUMBER: String = "810014687"

    // Derived headline: "Pay $500 to activate calculator for one week."
    val HEADLINE: String = "Pay $PRICE_DISPLAY to activate calculator for $DURATION_TEXT."

    // Derived primary button text: "Pay $500 to unlock it for one week."
    val PRIMARY_BUTTON_LABEL: String = "Pay $PRICE_DISPLAY to unlock it for $DURATION_TEXT."

    // Derived WhatsApp prefilled message: "Hi, I want to purchase calculator for one week for $500."
    val WHATSAPP_MESSAGE: String =
        "Hi, I want to purchase calculator for $DURATION_TEXT for $PRICE_DISPLAY."

    // Secondary dismiss button label
    const val SECONDARY_BUTTON_LABEL: String = "I will think later."

    /**
     * Builds the exact WhatsApp intent URI:
     * https://wa.me/<digits-only number>?text=<URL-encoded message>
     * Special characters like '$' (%24), ',' (%2C), ' ' (%20) are properly URL-encoded.
     */
    fun buildWhatsAppUri(): String {
        val encodedMessage = URLEncoder.encode(WHATSAPP_MESSAGE, "UTF-8")
            .replace("+", "%20")
        return "https://wa.me/$WHATSAPP_PHONE_NUMBER?text=$encodedMessage"
    }
}
