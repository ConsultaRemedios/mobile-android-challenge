import br.com.angelorobson.templatemvi.model.entities.GameEntity
import kotlin.random.Random

class GameEntityBuilder {

    data class Builder(
            private var idGame: Int = 0,
            private var title: String = "",
            private var image: String = "",
            private var discount: Double = 0.0,
            private var price: Double = 0.0
    ) {

        fun idGame(idGame: Int) =
                apply { this.idGame = idGame }

        fun title(title: String) = apply { this.title = title }
        fun image(image: String) = apply { this.image = image }
        fun discount(discount: Double) = apply { this.discount = discount }
        fun price(price: Double) = apply { this.price = price }

        fun oneGameEntity() = apply {
            idGame = Random(50).nextInt()
            title = "The Legend Of Zelda Breath of The Wild"
            image = "fs"
            discount = 100.0
            price = 350.0
        }

        fun build() = GameEntity(
                idGame = idGame,
                title = title,
                image = image,
                discount = discount,
                price = price
        )
    }
}