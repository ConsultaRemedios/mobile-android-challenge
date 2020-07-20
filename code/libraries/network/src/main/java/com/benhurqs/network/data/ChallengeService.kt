package com.benhurqs.network.data

import com.benhurqs.network.entities.Banner
import com.benhurqs.network.entities.Cart
import com.benhurqs.network.entities.GameDetail
import com.benhurqs.network.entities.Spotlight
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class ChallengeService {
    private val retrofit: Retrofit
    private val api: ChallengeAPI
    private val TIMEOUT: Long = 20

    init {

        val httpClient = OkHttpClient.Builder().apply {

            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG){
                val logging = HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)

                addInterceptor(logging).build()
            }
        }.build()


        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()

        api = retrofit.create(ChallengeAPI::class.java)
    }

    open fun getBanners(): Observable<List<Banner>?> = api.banners()
    open fun getSpotlights(): Observable<List<Spotlight>?> = api.spotlight()
    open fun getDetail(id: Int): Observable<GameDetail?> = api.detail(id)
    open fun getSearchSuggestions(query: String): Observable<List<GameDetail>?> = api.search(query)
    open fun checkout(checkout: Cart): Observable<List<GameDetail>?> = api.checkout(checkout)
}