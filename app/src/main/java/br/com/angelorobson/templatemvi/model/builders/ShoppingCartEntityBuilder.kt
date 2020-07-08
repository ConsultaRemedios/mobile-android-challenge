package br.com.angelorobson.templatemvi.model.builders

import GameEntityBuilder
import br.com.angelorobson.templatemvi.model.entities.GameEntity
import br.com.angelorobson.templatemvi.model.entities.ShoppingCartEntity
import kotlin.random.Random

class ShoppingCartEntityBuilder {

    data class Builder(
            var id: Int = 0,
            var totalWithDiscount: Double = 0.0,
            var totalWithoutDiscount: Double = 0.0,
            var quantity: Int = 0,
            var gameEntity: GameEntity? = null
    ) {

        fun id(id: Int) =
                apply { this.id = id }

        fun totalWithDiscount(totalWithDiscount: Double) = apply { this.totalWithDiscount = totalWithDiscount }
        fun totalWithoutDiscount(totalWithoutDiscount: Double) = apply { this.totalWithoutDiscount = totalWithoutDiscount }
        fun totalWithoutDiscount(quantity: Int) = apply { this.quantity = quantity }
        fun spotlight(gameEntity: GameEntity) = apply { this.gameEntity = gameEntity }

        fun oneShoppingCartEntity() = apply {
            id = Random(50).nextInt()
            totalWithDiscount = 0.0
            totalWithoutDiscount = 0.0
            quantity = 0
            gameEntity = GameEntityBuilder.Builder().oneGameEntity().build()
        }

        fun build() = ShoppingCartEntity(
                id = id,
                totalWithDiscount = totalWithDiscount,
                totalWithoutDiscount = totalWithoutDiscount,
                quantity = quantity,
                gameEntity = gameEntity!!
        )
    }
}