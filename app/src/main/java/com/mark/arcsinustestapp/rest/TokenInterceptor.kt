package com.mark.arcsinustestapp.rest

import com.mark.arcsinustestapp.BuildConfig
import com.mark.arcsinustestapp.extensions.toMd5
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor() : Interceptor {

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