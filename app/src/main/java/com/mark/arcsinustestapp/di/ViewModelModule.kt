package com.mark.arcsinustestapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.arcsinustestapp.di.utils.DaggerAwareViewModelFactory
import com.mark.arcsinustestapp.di.utils.ViewModelKey
import com.mark.arcsinustestapp.view_models.MarvelViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerAwareViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MarvelViewModel::class)
    abstract fun bindMarvelViewModel(viewModel: MarvelViewModel): ViewModel
}