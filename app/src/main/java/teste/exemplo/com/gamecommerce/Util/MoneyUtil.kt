package teste.exemplo.com.gamecommerce.Util

import java.text.NumberFormat
import java.util.*

object MoneyUtil {

     fun formatMoney(money: Double): String {
        val ptBr = Locale("pt", "BR")
        return NumberFormat.getCurrencyInstance(ptBr).format(money)
    }
}