package java.games.ecommerce.main.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.games.ecommerce.main.data.model.Banner
import java.games.ecommerce.main.data.model.Game

interface GameService {
    @GET("spotlight")
    suspend fun getGames() : List<Game>
    @GET("banners")
    suspend fun getBanners() : List<Banner>
    @GET("games/search")
    suspend fun searchGame(@Query("term") term: String) : List<Game>
}