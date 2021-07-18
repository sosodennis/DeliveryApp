package com.dw.deliveryapp.util


import org.junit.Assert
import org.junit.Test

class AmountUtilsTest {
    @Test
    fun formatAmount1DecimalPlaceWithDefaultFormat() {
        val formattedAmount = AmountUtils.format(10000.1)
        Assert.assertEquals("$10,000.1", formattedAmount)
    }

    @Test
    fun formatAmount2DecimalPlaceWithDefaultFormat() {
        val formattedAmount = AmountUtils.format(1000.11)
        Assert.assertEquals("$1,000.11", formattedAmount)
    }

    @Test
    fun formatAmount3DecimalPlaceWithDefaultFormat() {
        val formattedAmount = AmountUtils.format(1.125)
        Assert.assertEquals("$1.12", formattedAmount)
    }

    @Test
    fun formatAmount3DecimalPlaceRoundUpWithDefaultFormat() {
        val formattedAmount = AmountUtils.format(1.126)
        Assert.assertEquals("$1.13", formattedAmount)
    }

    @Test
    fun parseDoubleWithDefaultFormat() {
        val amount = AmountUtils.parseDouble("$1000.02")
        Assert.assertEquals(1000.02, amount, 0.0)
    }

    @Test
    fun parseDoubleFailWithDefaultFormat() {
        val amount = AmountUtils.parseDouble("1000.02")
        Assert.assertEquals(0.0, amount, 0.0)
    }
}