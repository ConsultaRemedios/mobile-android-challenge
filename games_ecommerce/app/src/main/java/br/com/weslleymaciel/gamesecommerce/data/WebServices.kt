package br.com.weslleymaciel.gamesecommerce.data

import br.com.weslleymaciel.gamesecommerce.common.models.Banner
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import br.com.weslleymaciel.gamesecommerce.common.models.SearchGame
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface WebService {
    @GET("banners")
    fun getBanners(): Single<List<Banner>>
    @GET("spotlight")
    fun getSpotlight(): Single<List<Game>>
    @GET("games/{id}")
    fun getGameDetail(@Path("id") id: Number): Single<Game>
    @GET("games/search")
    fun searchGame(@Query("term") term: String): Single<List<SearchGame>>
    @POST("checkout")
    fun checkout(): Single<ResponseBody>
}

object WebServiceFactory {

    fun create(): WebService {
        val clientBuilder = OkHttpClient.Builder().addInterceptor(Interceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)

        val okHttpClient = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl("https://api-mobile-test.herokuapp.com/api/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebService::class.java)
    }
}