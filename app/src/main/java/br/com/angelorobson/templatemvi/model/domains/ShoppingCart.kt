package br.com.angelorobson.templatemvi.model.domains

data class ShoppingCart(
        val id: Int = 0,
        val totalWithDiscount: Double = 0.0,
        val totalWithoutDiscount: Double = 0.0,
        val quantity: Int = 0,
        val spotlight: Spotlight = Spotlight()
)