package teste.exemplo.com.gamecommerce.Util

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import teste.exemplo.com.gamecommerce.Rest.RestAPI


object RestUtil {
    val api = Retrofit.Builder()
        .baseUrl("https://game-checkout.herokuapp.com/")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RestAPI::class.java)
}