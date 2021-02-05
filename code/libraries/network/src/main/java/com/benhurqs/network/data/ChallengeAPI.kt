package com.benhurqs.network.data

import com.benhurqs.network.entities.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*


interface ChallengeAPI {

    @GET("banners")
    fun banners(): Observable<List<Banner>?>

    @GET("spotlight")
    fun spotlight(): Observable<List<Spotlight>?>

    @GET("games/{game_id}")
    fun detail(@Path("game_id") gameID: Int?): Observable<Spotlight?>

    @GET("games/search")
    fun search(@Query("term") query: String?): Observable<List<Suggestion>?>

    @POST("checkout")
    fun checkout(): Observable<ResponseBody?>
}