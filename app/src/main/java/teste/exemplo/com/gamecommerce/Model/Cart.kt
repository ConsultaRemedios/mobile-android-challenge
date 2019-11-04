package teste.exemplo.com.gamecommerce.Model

object Cart {
    var items: ArrayList<GameAddedToCart> = ArrayList()
    var totalItems: Int = 0
    var totalPrice: Double = 0.0
    var totalGamesPrice: Double = 0.0
    var totalTax: Double = 0.0
}