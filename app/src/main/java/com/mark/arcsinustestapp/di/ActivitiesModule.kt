package com.mark.arcsinustestapp.di

import com.mark.arcsinustestapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector()
    internal abstract fun mainActivity() : MainActivity

}