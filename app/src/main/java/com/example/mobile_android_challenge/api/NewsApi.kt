package com.example.mobile_android_challenge.api


import com.example.mobile_android_challenge.model.Game
import com.example.mobile_android_challenge.model.GameItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface NewsApi {
    companion object {
        const val URL = "https://game-checkout.herokuapp.com/"
        const val API_KEY = "QceNFo1gHd09MJDzyswNqzStlxYGBzUG"
    }

    @GET("game")
    fun games(
        @Header("Token") apiKey: String): Observable<List<Game>>

    @GET("game/{id}")
    fun gameItem(
        @Header("Token") apiKey: String, @Path("id") id: Long): Observable<GameItem>

    @POST("checkout}")
    fun gameCheckout(
        @Header("Token") apiKey: String): Observable<String>

}