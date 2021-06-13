package com.mark.arcsinustestapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mark.arcsinustestapp.BuildConfig
import com.mark.arcsinustestapp.rest.TokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor

@Module
class NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun providesTokenInterceptor(): TokenInterceptor {
        return TokenInterceptor()
    }
}