package br.com.angelorobson.templatemvi.view.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.convertToCurrency(language: String = "pt", country: String = "BR"): String {
    val ptBr = Locale(language, country)
    return NumberFormat.getCurrencyInstance(ptBr).format(this)
}