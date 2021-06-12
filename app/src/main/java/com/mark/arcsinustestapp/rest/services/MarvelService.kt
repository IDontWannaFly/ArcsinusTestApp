package com.mark.arcsinustestapp.rest.services

import com.mark.arcsinustestapp.rest.models.marvel.GetCharactersRes
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") namePrefix: String?,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 10,
        @Query("orderBy") order: String = "name"
    ) : Observable<GetCharactersRes>

}