package br.com.weslleymaciel.gamesecommerce.common.utils

import java.text.NumberFormat
import java.util.*

fun numberToPrice(price: Number): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("BRL")

    return format.format(price)
}