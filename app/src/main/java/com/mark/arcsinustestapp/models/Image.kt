package com.mark.arcsinustestapp.models

import com.google.gson.annotations.Expose
import io.realm.RealmObject
import io.realm.annotations.Ignore

open class Image : RealmObject() {

    @Expose
    var path: String? = null
    @Expose
    var extension: String? = null

    @Ignore
    var url: String = "$path.$extension"
        get() {
            return "$path.$extension"
        }

}