package com.mark.arcsinustestapp.extensions

import com.google.gson.Gson
import com.mark.arcsinustestapp.rest.ServerError
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.adapter.rxjava2.Result.response
import java.io.IOException

fun Throwable.getErrorBody() : ServerError?{
    if (this is HttpException) {
        val body: ResponseBody? = response()?.errorBody()

        val errorConverter = Gson()
        // Convert the error body into our Error type.
        return try {
            val error: ServerError? = body?.let { errorConverter.fromJson(it.string(), ServerError::class.java) }
            //Timber.e("ERROR: %s", error.message)
            error
        } catch (e1: IOException) {
            //Timber.e(e1)
            null
        }
    }
    return null
}