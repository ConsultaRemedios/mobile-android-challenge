package br.com.weslleymaciel.gamesecommerce.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface WebService {
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