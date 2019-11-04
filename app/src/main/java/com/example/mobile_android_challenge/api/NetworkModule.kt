package com.example.mobile_android_challenge.api

import com.example.mobile_android_challenge.BuildConfig
import com.example.mobile_android_challenge.api.ApiClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor

@Module()
class NetworkModule {
    @Provides
    @Singleton
    fun providesApi(): ApiClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging);

        val gson = GsonBuilder()
            .create()

        val gitApi = Retrofit.Builder()
            .baseUrl(NewsApi.URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NewsApi::class.java)
        return ApiClient(gitApi)
    }
}