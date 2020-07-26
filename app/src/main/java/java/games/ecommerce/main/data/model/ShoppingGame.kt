package java.games.ecommerce.main.data.model

import java.math.BigDecimal

data class ShoppingGame(
    val id: Int,
    val title: String,
    val price: Double,
    val discount: Double,
    val image: String,
    var amount: Int
)

fun Game.toShoppingGame(): ShoppingGame {
    return with(this) {
        ShoppingGame(
            id = this.id,
            title = this.title,
            price = this.price,
            discount = this.discount,
            image = this.image,
            amount = 1
        )
    }
}