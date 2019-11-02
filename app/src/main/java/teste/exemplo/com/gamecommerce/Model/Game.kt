package teste.exemplo.com.gamecommerce.Model

import com.squareup.moshi.Json;

class Game {
    @Json(name = "id")
    val id = 0
    @Json(name = "name")
    val name = ""
    @Json(name = "price")
    val price = 0.0
    @Json(name = "platform")
    val platform = ""
    @Json(name = "image")
    val image = ""
}