package br.com.angelorobson.templatemvi.model.builders

import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import kotlin.random.Random

class ShoppingCartBuilder {

    data class Builder(
            private var id: Int = 0,
            private var totalWithDiscount: Double = 0.0,
            private var totalWithoutDiscount: Double = 0.0,
            private var quantity: Int = 0,
            private var spotlight: Spotlight = Spotlight()
    ) {

        fun id(id: Int) =
                apply { this.id = id }

        fun totalWithDiscount(totalWithDiscount: Double) = apply { this.totalWithDiscount = totalWithDiscount }
        fun totalWithoutDiscount(totalWithoutDiscount: Double) = apply { this.totalWithoutDiscount = totalWithoutDiscount }
        fun totalWithoutDiscount(quantity: Int) = apply { this.quantity = quantity }
        fun spotlight(spotlight: Spotlight) = apply { this.spotlight = spotlight }

        fun oneShoppingCart() = apply {
            id = Random(50).nextInt()
            totalWithDiscount = 0.0
            totalWithoutDiscount = 0.0
            quantity = 0
            spotlight = SpotlightBuilder.Builder().oneSpotlight().build()
        }

        fun build() = ShoppingCart(
                id = id,
                totalWithDiscount = totalWithDiscount,
                totalWithoutDiscount = totalWithoutDiscount,
                quantity = quantity,
                spotlight = spotlight
        )
    }
}