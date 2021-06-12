package com.mark.arcsinustestapp.rest.models.marvel

import com.google.gson.annotations.Expose
import com.mark.arcsinustestapp.models.Character

class GetCharactersRes {
    @Expose
    val code: Int? = null
    @Expose
    val status: String? = null
    @Expose
    val data: CharacterDataContainer? = null

    class CharacterDataContainer {
        @Expose
        val results: List<Character>? = null
    }
}