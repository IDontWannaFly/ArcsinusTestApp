package com.mark.arcsinustestapp.view_models

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mark.arcsinustestapp.Application
import com.mark.arcsinustestapp.R
import com.mark.arcsinustestapp.extensions.getErrorBody
import com.mark.arcsinustestapp.extensions.plusAssign
import com.mark.arcsinustestapp.models.Character
import com.mark.arcsinustestapp.repositories.MarvelRepoLocal
import com.mark.arcsinustestapp.repositories.MarvelRepoRemote
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MarvelViewModel @Inject constructor(
    private val app: Application,
    private val repo: MarvelRepoRemote,
    private val repoLocal: MarvelRepoLocal
) : AndroidViewModel(app) {

    sealed class Event{
        class OnError(val msg: String) : Event()
        class OnItemSelected(val item: Character) : Event()
    }

    private val compositeDisposable = CompositeDisposable()
    private val searchHandler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        getItems(1)
    }

    val event = MutableLiveData<Event>()
    val items = MutableLiveData<ArrayList<Character>>(arrayListOf())
    val searchText = object : MutableLiveData<String>(){
        override fun setValue(value: String?) {
            super.setValue(value)

            if(!value.isNullOrEmpty() && value.length < 3)
                return
            searchHandler.removeCallbacks(searchRunnable)
            searchHandler.postDelayed(searchRunnable, 500)
        }
    }
    val isLoading = MutableLiveData<Boolean>()

    fun getItems(page: Int){
        isLoading.value = true
        compositeDisposable += repo.getCharacters(searchText.value ?: "", page).subscribe({
            if(page == 1) {
                items.value?.clear()
                //items.value = items.value
            }
            items.value?.addAll(it)
        }, {
            isLoading.value = false
            it.getErrorBody()?.status?.let { msg ->
                event.value = Event.OnError(msg)
                return@let
            }
            event.value = Event.OnError(app.getString(R.string.error_occurred))
        }, {
            isLoading.value = false
            items.value = items.value
        })
    }

    fun selectItem(item: Character){
        repoLocal.saveCharacter(item)
        event.value = Event.OnItemSelected(item)
    }

    override fun onCleared() {
        super.onCleared()

        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

}