package java.games.ecommerce.main.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.games.ecommerce.main.data.db.dao.ShoppingGameDao
import java.games.ecommerce.main.data.model.ShoppingGame
import java.math.BigDecimal

@Entity(tableName = "shopping")
data class ShoppingGameEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val discount: Double,
    val image: String,
    val amount: Int
)

fun ShoppingGame.toShoppingGameEntity() : ShoppingGameEntity {
    return with(this) {
        ShoppingGameEntity(
            id = this.id,
            title = this.title,
            price = this.price,
            discount = this.discount,
            image = this.image,
            amount = this.amount
        )
    }
}

fun ShoppingGameEntity.toShoppingGame() : ShoppingGame {
    return with(this) {
        ShoppingGame(
            id = this.id,
            title = this.title,
            price = this.price,
            discount = this.discount,
            image = this.image,
            amount = this.amount
        )
    }
}