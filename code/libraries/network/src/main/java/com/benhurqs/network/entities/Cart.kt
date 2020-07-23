package com.benhurqs.network.entities

import java.io.Serializable

class Cart : Serializable, Spotlight() {
    var qtd: Int = 0

    companion object{
        fun convertToCart(spotlight: Spotlight): Cart{
            return Cart().apply {
                this.id = spotlight.id
                this.title = spotlight.title
                this.publisher = spotlight.publisher
                this.image = spotlight.image
                this.discount = spotlight.discount
                this.price = spotlight.price
                this.description = spotlight.description
                this.rating = spotlight.rating
                this.stars = spotlight.stars
                this.reviews = spotlight.reviews
                this.qtd = 1
            }
        }
    }

}