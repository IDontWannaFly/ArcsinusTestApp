package com.mark.arcsinustestapp.rest.models

import com.google.gson.annotations.Expose

class ServerError{
    @Expose
    val code: Int? = null
    @Expose
    val status: String? = null
}