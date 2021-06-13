package com.mark.arcsinustestapp.repositories

import com.mark.arcsinustestapp.Application
import com.mark.arcsinustestapp.models.Character
import com.mark.arcsinustestapp.rest.RestManager
import com.mark.arcsinustestapp.rest.services.MarvelService
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MarvelRepoRemote {

    private val restManager = RestManager.instance
    private val service = restManager.createService(RestManager.RestOptions(RestManager.MAIN_URL), MarvelService::class.java)
    private val localRepo = MarvelRepoLocal()
    private val app = Application.instance

    fun getCharacters(name: String, page: Int) : Observable<List<Character>>{
        return if(app.isNetworkAvailable) service.getCharacters(if(name.isEmpty() || name.length < 3) null else name , (page - 1) * 10).flatMap {
            return@flatMap Observable.create(ObservableOnSubscribe <List<Character>> { emitter ->
                emitter.onNext(it.data?.results ?: listOf())
                return@ObservableOnSubscribe emitter.onComplete()
            })
        }.observeOn(AndroidSchedulers.mainThread())
        else
            Observable.just(localRepo.getCharacters(name, page))
    }

}