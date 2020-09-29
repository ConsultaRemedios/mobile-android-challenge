package br.com.challenge.consultaremedios.utils

import java.text.NumberFormat
import java.util.*

class GenericUtils {
    companion object {
        fun brazilianNumberFormat() : NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
    }
}