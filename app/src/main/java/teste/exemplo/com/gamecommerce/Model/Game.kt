package teste.exemplo.com.gamecommerce.Model

import com.squareup.moshi.Json

class Game {
    @Json(name = "id")
    var id = 0
    @Json(name = "name")
    var name = ""
    @Json(name = "price")
    var price = 0.0
    @Json(name = "platform")
    var platform = ""
    @Json(name = "image")
    var image = ""
    @Json(name = "description")
    var description = ""
}