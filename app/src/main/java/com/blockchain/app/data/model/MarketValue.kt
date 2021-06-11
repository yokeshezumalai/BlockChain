package com.blockchain.app.data.model

import java.text.NumberFormat
import java.util.*

data class MarketValue(
    val dateMillis: Long,
    val price: Double
) {

    var currency: Currency = Currency.getInstance("USD")

    companion object {
        const val MAX_FRACTION_DIGITS = 2
    }

    fun getPriceStringFormat(): String {
        val priceNumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

        priceNumberFormat.maximumFractionDigits = MAX_FRACTION_DIGITS
        priceNumberFormat.currency = this.currency

        return priceNumberFormat.format(price)
    }
}