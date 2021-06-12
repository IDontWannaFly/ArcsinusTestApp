package com.mark.arcsinustestapp.rest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import java.util.*

object RestUtils {

    fun initGson(): Gson {
        val builder = GsonBuilder()
        builder.excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Date::class.java, DateTypeAdapter())
        builder.setLenient()
        return builder.create()
    }

}