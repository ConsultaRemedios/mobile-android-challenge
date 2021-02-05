package com.benhurqs.network.data

import com.benhurqs.network.BuildConfig
import com.benhurqs.network.entities.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
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
    open fun getDetail(id: Int?): Observable<Spotlight?> = api.detail(id)
    open fun getSearchSuggestions(query: String?): Observable<List<Suggestion>?> = api.search(query)
    open fun checkout(): Observable<ResponseBody?> = api.checkout()
}