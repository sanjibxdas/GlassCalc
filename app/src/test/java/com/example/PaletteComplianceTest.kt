package com.example

import com.example.ui.theme.CalcBlack
import com.example.ui.theme.CalcOrange
import com.example.ui.theme.CalcWhite
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class PaletteComplianceTest {

    @Test
    fun testComposeThemeColorsAreStrictlyThreeHues() {
        // Hex values of the three base colors
        assertEquals(0xFF000000.toInt(), CalcBlack.value.toInt())
        assertEquals(0xFFFF9F0A.toInt(), CalcOrange.value.toInt())
        assertEquals(0xFFFFFFFF.toInt(), CalcWhite.value.toInt())
    }

    @Test
    fun testXmlResourcesContainNoMoreThanThreeColors() {
        // Locate res/values/colors.xml
        val possiblePaths = listOf(
            File("app/src/main/res/values/colors.xml"),
            File("src/main/res/values/colors.xml"),
            File("/app/src/main/res/values/colors.xml")
        )
        val colorsFile = possiblePaths.firstOrNull { it.exists() }
            ?: throw IllegalStateException("Could not find colors.xml in any expected path")

        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc = builder.parse(colorsFile)
        val colorNodes = doc.getElementsByTagName("color")

        // Must define at most three colors
        assertTrue("colors.xml must define at most 3 colors", colorNodes.length <= 3)

        val allowedHexSet = setOf(
            "#FF000000", "#000000",
            "#FFFF9F0A", "#FF9F0A",
            "#FFFFFFFF", "#FFFFFF"
        )

        for (i in 0 until colorNodes.length) {
            val node = colorNodes.item(i)
            val hex = node.textContent.trim().uppercase()
            assertTrue(
                "Color '$hex' in colors.xml must be one of the three allowed palette colors: $allowedHexSet",
                allowedHexSet.contains(hex)
            )
        }
    }
}
