package br.com.challenge.consultaremedios.api.mobiletest

import br.com.challenge.consultaremedios.db.entity.CartItem
import br.com.challenge.consultaremedios.model.Banner
import br.com.challenge.consultaremedios.model.Game
import retrofit2.Call
import retrofit2.http.*

interface Endpoints {

    @GET("banners")
    fun getBanners(): Call<List<Banner>>

    @GET("spotlight")
    fun getSpotlight(): Call<List<Game>>

    @GET("games/{id}")
    fun getGameDetails(@Path("id") id:Int): Call<Game>

    @GET("games/search")
    fun searchGames(@Query("term") query:String): Call<List<Game>>

    @POST("checkout")
    fun postCheckout(@Body body:List<CartItem>): Call<Void>
}