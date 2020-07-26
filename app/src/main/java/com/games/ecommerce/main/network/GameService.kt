package com.games.ecommerce.main.network

import com.games.ecommerce.main.data.model.Banner
import com.games.ecommerce.main.data.model.Game
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {
    @GET("spotlight")
    suspend fun getGames(): List<Game>

    @GET("banners")
    suspend fun getBanners(): List<Banner>

    @GET("games/search")
    suspend fun searchGame(@Query("term") term: String): List<Game>

    @GET("games/{id}")
    suspend fun gameById(@Path("id") id: Int): Game

    @POST("checkout")
    suspend fun checkout()
}