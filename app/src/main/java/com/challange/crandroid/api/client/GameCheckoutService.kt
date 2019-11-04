package com.challange.crandroid.api.client

import com.challange.crandroid.data.response.Game
import com.challange.crandroid.data.response.GameDetails
import com.challange.crandroid.singleton.Cart
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GameCheckoutService {

    @GET("game")
    fun getGames():Call<List<Game>>

    @GET("game/{gameId}")
    fun getGameDetails(@Path("gameId") gameId:Int):Call<GameDetails>

    @POST("checkout")
    fun checkout(@Body cart: Cart):Call<Void>
}