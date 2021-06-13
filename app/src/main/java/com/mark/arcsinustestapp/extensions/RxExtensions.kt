package com.mark.arcsinustestapp.extensions

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(item: Disposable){
    add(item)
}