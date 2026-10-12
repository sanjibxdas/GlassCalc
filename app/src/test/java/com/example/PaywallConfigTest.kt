package com.example

import com.example.paywall.PaywallConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PaywallConfigTest {

    @Test
    fun testPriceConsistencyAcrossAllStrings() {
        // Price amount constant
        assertEquals(500, PaywallConfig.PRICE_AMOUNT)
        assertEquals("$500", PaywallConfig.PRICE_DISPLAY)

        // All three strings must strictly contain the exact price display "$500"
        assertTrue(
            "Headline must derive from price display",
            PaywallConfig.HEADLINE.contains(PaywallConfig.PRICE_DISPLAY)
        )
        assertTrue(
            "Primary button label must derive from price display",
            PaywallConfig.PRIMARY_BUTTON_LABEL.contains(PaywallConfig.PRICE_DISPLAY)
        )
        assertTrue(
            "WhatsApp message must derive from price display",
            PaywallConfig.WHATSAPP_MESSAGE.contains(PaywallConfig.PRICE_DISPLAY)
        )

        // Exact text verification
        assertEquals(
            "Pay $500 to activate calculator for one week.",
            PaywallConfig.HEADLINE
        )
        assertEquals(
            "Pay $500 to unlock it for one week.",
            PaywallConfig.PRIMARY_BUTTON_LABEL
        )
        assertEquals(
            "Hi, I want to purchase calculator for one week for $500.",
            PaywallConfig.WHATSAPP_MESSAGE
        )
        assertEquals(
            "I will think later.",
            PaywallConfig.SECONDARY_BUTTON_LABEL
        )
    }

    @Test
    fun testWhatsAppUriBuilder() {
        val uri = PaywallConfig.buildWhatsAppUri()

        // Exact phone number: 810014687
        assertEquals("810014687", PaywallConfig.WHATSAPP_PHONE_NUMBER)
        assertTrue("URI must start with https://wa.me/810014687?text=", uri.startsWith("https://wa.me/810014687?text="))

        // Must encode '$' as %24
        assertTrue("URI must properly encode '$' as %24", uri.contains("%24500"))

        // Must encode commas as %2C
        assertTrue("URI must properly encode ',' as %2C", uri.contains("Hi%2C"))

        // Exact expected URI
        val expectedUri = "https://wa.me/810014687?text=Hi%2C%20I%20want%20to%20purchase%20calculator%20for%20one%20week%20for%20%24500."
        assertEquals(expectedUri, uri)
    }
}
