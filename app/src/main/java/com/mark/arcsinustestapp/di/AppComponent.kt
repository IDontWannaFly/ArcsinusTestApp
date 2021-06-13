package com.mark.arcsinustestapp.di

import com.mark.arcsinustestapp.Application
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class, NetworkModule::class, AppModule::class, AppConstantsModule::class, ViewModelModule::class]
)
interface AppComponent : AndroidInjector<Application> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<Application>

}