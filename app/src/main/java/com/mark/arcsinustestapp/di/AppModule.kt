package com.mark.arcsinustestapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class AppModule {

    @Provides
    fun providesContext(application: com.mark.arcsinustestapp.Application): Context {
        return application.applicationContext
    }

    @Provides
    fun providesApp(application: com.mark.arcsinustestapp.Application): Application {
        return application
    }

    @Provides
    fun providesRealm(): Realm {
        return Realm.getDefaultInstance()
    }

}