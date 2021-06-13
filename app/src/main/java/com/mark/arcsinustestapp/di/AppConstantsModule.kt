package com.mark.arcsinustestapp.di

import dagger.Module
import dagger.Provides

@Module
class AppConstantsModule {

    @Provides
    fun provideNetworkConstants(): NetworkConstants {
        return NetworkConstants()
    }
}