package com.games.ecommerce.main.data.model

data class ShoppingGame(
    val id: Int,
    val title: String,
    val price: Double,
    val originalPrice: Double,
    val image: String,
    var amount: Int
)

fun GameRepositoryResponse.toShoppingGame(): ShoppingGame {
    return with(this) {
        ShoppingGame(
            id = this.id,
            title = this.title,
            price = this.price,
            originalPrice = this.originalPrice,
            image = this.image,
            amount = 1
        )
    }
}