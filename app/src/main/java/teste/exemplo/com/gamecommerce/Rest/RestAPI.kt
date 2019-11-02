package teste.exemplo.com.gamecommerce.Rest

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import teste.exemplo.com.gamecommerce.Model.Game
import teste.exemplo.com.gamecommerce.Model.GamesResponse
import java.util.*


interface RestAPI {

    @GET("game/{id}")
    fun getGameById(
        @Path("id") id: Int?,
        @Header("token") token: String
    ): Observable<Game>

    @GET("game")
    fun getGames(
        @Header("token") token: String
    ): Observable<List<Game>>
}