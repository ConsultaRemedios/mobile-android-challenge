package com.challange.crandroid.api

import com.challange.crandroid.BuildConfig
import com.challange.crandroid.api.client.GameCheckoutService
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameCheckoutServiceInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://game-checkout.herokuapp.com/")
        .addConverterFactory((GsonConverterFactory.create()))
        .client(getHttpClient())
        .build()

    fun gameCheckoutService() = retrofit.create(GameCheckoutService::class.java)

    private fun getHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request: Request = original.newBuilder()
                .header("Token", BuildConfig.GameCheckoutApiKey)
                .build()
            chain.proceed(request)
        }

        return httpClient.build()
    }
}