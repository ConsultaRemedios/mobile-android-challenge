package mazer.arthur.gamingshop.models

data class GameDetails(val id: Int, val title: String, val publisher: String, val image: String,
                       val discount: Int, val price: Float, val description: String, val rating: Double, val stars: Int, val reviews: Int)