package com.mark.arcsinustestapp.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MarvelViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MarvelViewModel::class.java))
            return MarvelViewModel() as T

        throw IllegalArgumentException("Wrong view model")
    }
}