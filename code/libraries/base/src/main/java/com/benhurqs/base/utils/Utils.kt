package com.benhurqs.base.utils

import java.text.NumberFormat
import java.util.*

object Utils {

    const val EMPTY_STRING = ""
    private val br = Locale("pt", "BR")

    fun formatPrice(price: Number?): String {
        val currency: Currency?
        if (price == null) return EMPTY_STRING
        //		Locale loc = ZoomLocaleProvider.getRealLocale();
        //		Locale.getDefault().getDisplayLanguage();
        val loc = br
        val nf = NumberFormat.getCurrencyInstance(loc)
        var symbol: String? = null
        currency = nf.currency

        if (currency != null) {
            symbol = currency.symbol
        }

        var formattedSTR = nf.format(price)
        if (!isEmpty(symbol) && !symbol!!.contains(" ")) {
            formattedSTR = formattedSTR.replace(symbol, "$symbol ")
        }

        return formattedSTR
    }


    fun isEmpty(s: String?): Boolean {
        return s == null || s.trim { it <= ' ' } == EMPTY_STRING
    }
}