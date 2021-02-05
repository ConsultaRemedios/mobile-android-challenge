package br.com.angelorobson.templatemvi.model.builders

import br.com.angelorobson.templatemvi.model.domains.Spotlight
import kotlin.random.Random

class SpotlightBuilder {

    data class Builder(
            private var id: Int = 0,
            private var title: String = "",
            private var publisher: String? = "",
            private var image: String? = "",
            private var discount: Double = 0.0,
            private var price: Double = 0.0,
            private var description: String? = "",
            private var rating: Float? = 0f,
            private var stars: Int? = 0,
            private var reviews: Int? = 0
    ) {

        fun id(id: Int) =
                apply { this.id = id }

        fun title(title: String) = apply { this.title = title }
        fun publisher(publisher: String) = apply { this.publisher = publisher }
        fun image(image: String) = apply { this.image = image }
        fun discount(discount: Double) = apply { this.discount = discount }
        fun price(price: Double) = apply { this.price = price }
        fun description(description: String) = apply { this.description = description }
        fun rating(rating: Float) = apply { this.rating = rating }
        fun stars(stars: Int) = apply { this.stars = stars }
        fun reviews(reviews: Int) = apply { this.reviews = reviews }

        fun oneSpotlight() = apply {
            id = Random(50).nextInt()
            title = "The Legend Of Zelda Breath of The Wild"
            publisher = "Nintendo"
            image = "https://switch-brasil.com/wp-content/uploads/2020/02/Zelda-Breath-of-the-Wild_Keyart.jpg"
            discount = 100.0
            price = 350.0
            description = "The Legend of Zelda: Breath of the Wild é um jogo eletrônico..."
            rating = 5f
            stars = 5
            reviews = 450
        }

        fun build() = Spotlight(
                id = id,
                title = title,
                publisher = publisher,
                image = image,
                discount = discount,
                price = price,
                description = description,
                rating = rating,
                stars = stars,
                reviews = reviews
        )
    }
}