package com.example.app.tradersapp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class TradersUnitTest {
    @Test
    fun rounding_isCorrect(){
        val currencyConverter = CurrencyConverterFragment()
        val expected = "1.24"
        val actual = currencyConverter.round(1.2368484)
        assertEquals(expected,actual)
    }

    @Test
    fun swapCurrencies_isCorrect(){
        val currencyConverter = CurrencyConverterFragment()
        val expectedCurrency1 = currencyConverter.currency2
        val expectedCurrency2 = currencyConverter.currency1

        currencyConverter.swapCurr()

        val actualCurrency1 = currencyConverter.currency1
        val actualCurrency2 = currencyConverter.currency2

        assertEquals(expectedCurrency1, actualCurrency1)
        assertEquals(expectedCurrency2, actualCurrency2)


    }

    @Test
    fun reverseRate_isCorrect(){
        val currencyConverter = CurrencyConverterFragment()
        val expectedRate = 0.5
        currencyConverter.rate = 2.0
        currencyConverter.reverseRate()

        val actualRate = currencyConverter.rate
        assertEquals(expectedRate, actualRate, 0.001)
    }
}