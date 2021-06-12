package com.mark.arcsinustestapp.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.arcsinustestapp.models.Character

class MarvelViewModel : ViewModel() {

    sealed class Event{
        class OnError(val msg: String) : Event()
    }

    val event = MutableLiveData<Event>()
    val items = MutableLiveData<ArrayList<Character>>(arrayListOf())

}