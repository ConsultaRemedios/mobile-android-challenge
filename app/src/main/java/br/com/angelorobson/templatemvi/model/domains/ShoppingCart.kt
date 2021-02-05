package br.com.angelorobson.templatemvi.model.domains

data class ShoppingCart(
        val id: Int = 0,
        var totalWithDiscount: Double = 0.0,
        var totalWithoutDiscount: Double = 0.0,
        var quantity: Int = 0,
        val spotlight: Spotlight = Spotlight()
)