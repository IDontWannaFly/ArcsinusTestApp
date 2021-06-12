package com.mark.arcsinustestapp.rest

import com.mark.arcsinustestapp.BuildConfig
import com.mark.arcsinustestapp.extensions.toMd5
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit

class RestManager {

    companion object{

        const val MAIN_URL = "https://gateway.marvel.com/v1/public/"
        const val MAIN_URL_CORE = "https://gateway.marvel.com"

        val instance = RestManager()
    }

    private val interceptor = TokenInterceptor()

    fun <T> createService(options: RestOptions, service: Class<T>) : T{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(
            if(BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )
        val httpClient = OkHttpClient.Builder().run {
            readTimeout(options.timeout, TimeUnit.MILLISECONDS)
            connectTimeout(options.timeout, TimeUnit.MILLISECONDS)
            addInterceptor(logging)
            addInterceptor(interceptor)
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(options.baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    RestUtils.initGson()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(httpClient)
            .build()
        return retrofit.create(service)
    }

    class TokenInterceptor : Interceptor{

        override fun intercept(chain: Interceptor.Chain): Response {
            val timestamp = Date().toString()
            val original = chain.request()
            val request = original.newBuilder()
                .url(original.url.newBuilder()
                    .addQueryParameter("ts", timestamp)
                    .addQueryParameter("apikey", BuildConfig.API_KEY_PUBLIC)
                    .addQueryParameter("hash", "${timestamp}${BuildConfig.API_KEY_PRIVATE}${BuildConfig.API_KEY_PUBLIC}".toMd5())
                    .build())
                .method(original.method, original.body)
                .build()
            return chain.proceed(request)
        }
    }

    class RestOptions @JvmOverloads constructor(var baseUrl: String = MAIN_URL) {
        var timeout: Long = 0
        var mainToken: Boolean = false
        var token: String? = null

        init {
            timeout = 20000
            mainToken = true
        }

        fun baseUrl(baseUrl: String) : RestOptions{
            this.baseUrl = baseUrl
            return this
        }

        fun token(token: String) : RestOptions{
            this.token = token
            return this
        }

        fun mainToken(isMain: Boolean) : RestOptions{
            this.mainToken = isMain
            return this
        }

        fun timeout(timeout: Long) : RestOptions{
            this.timeout = timeout
            return this
        }
    }

}