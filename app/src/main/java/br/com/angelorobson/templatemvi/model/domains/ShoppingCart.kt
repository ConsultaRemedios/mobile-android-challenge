package br.com.angelorobson.templatemvi.model.domains

data class ShoppingCart(
        val id: Int = 0,
        val total: Double,
        val quantity: Int,
        val spotlight: Spotlight
)