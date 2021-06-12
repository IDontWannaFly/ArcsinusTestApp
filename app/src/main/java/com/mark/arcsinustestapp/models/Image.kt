package com.mark.arcsinustestapp.models

import com.google.gson.annotations.Expose
import io.realm.RealmObject

open class Image : RealmObject() {

    @Expose
    var path: String? = null
    @Expose
    var extension: String? = null

}