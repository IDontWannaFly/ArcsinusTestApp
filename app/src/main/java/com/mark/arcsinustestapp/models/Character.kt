package com.mark.arcsinustestapp.models

import com.google.gson.annotations.Expose
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Character : RealmObject() {

    @PrimaryKey
    @Expose
    var id: Int = 0
    @Expose
    var name: String? = null
    @Expose
    var description: String? = null
    @Expose
    var thumbnail: Image? = null

}