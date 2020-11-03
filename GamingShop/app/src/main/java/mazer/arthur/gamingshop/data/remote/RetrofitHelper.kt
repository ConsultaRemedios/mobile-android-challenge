package mazer.arthur.gamingshop.data.remote

import android.util.Log
import mazer.arthur.gamingshop.BuildConfig
import mazer.arthur.gamingshop.data.remote.ApiConstants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    val api: ApiNetwork = createRetrofitInstance()
        .create(
        ApiNetwork::class.java)

    private fun createRetrofitInstance(): Retrofit {
        val builder = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Log.d("HttpLogginInterceptor", message) }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(httpLoggingInterceptor)
        }

        val client = builder.build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}
