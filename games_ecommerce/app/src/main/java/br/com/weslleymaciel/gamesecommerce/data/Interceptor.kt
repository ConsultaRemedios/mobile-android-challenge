package br.com.weslleymaciel.gamesecommerce.data

import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Interceptor : okhttp3.Interceptor{

    private var request: Request? = null
    private var response: Response? = null
    lateinit var chain: okhttp3.Interceptor.Chain
    private var url: HttpUrl? = null

    @Throws(IOException::class)
    override fun intercept(chain: okhttp3.Interceptor.Chain): Response {

        this.chain = chain
        request = chain.request()

        val originalHttpUrl = request!!.url()
        url = originalHttpUrl.newBuilder().build()

        val requestBuilder = request!!.newBuilder()
                .url(url!!)
            request = requestBuilder.build()
        response = chain.proceed(request!!)

        return response as Response
    }
}