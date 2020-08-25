package com.games.ecommerce.main.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.games.ecommerce.main.data.model.ShoppingGame

@Entity(tableName = "shopping")
data class ShoppingGameEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val originalPrice: Double,
    val image: String,
    val amount: Int
)

fun ShoppingGame.toShoppingGameEntity(): ShoppingGameEntity {
    return ShoppingGameEntity(
        id = this.id,
        title = this.title,
        price = this.price,
        originalPrice = this.originalPrice,
        image = this.image,
        amount = this.amount
    )

}

fun ShoppingGameEntity.toShoppingGame(): ShoppingGame {
    return ShoppingGame(
        id = this.id,
        title = this.title,
        price = this.price,
        originalPrice = this.originalPrice,
        image = this.image,
        amount = this.amount
    )
}