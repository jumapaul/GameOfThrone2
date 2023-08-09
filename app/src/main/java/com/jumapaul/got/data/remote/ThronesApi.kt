package com.jerimkaura.got.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import com.jerimkaura.got.data.local.entities.Character
import com.jerimkaura.got.data.local.entities.Continent

interface ThronesApi {

    companion object{
        const val GET_ALL_CHARACTERS = "Characters"
        const val GET_ALL_CONTINENTS = "Continents"
        const val GET_SINGLE_CHARACTER = "Characters/{id}"
        const val GET_SINGLE_CONTINENT = "Continents/{id}"

    }

    @GET(GET_ALL_CHARACTERS)
    suspend fun getAllCharacters(): List<Character>

    @GET(GET_SINGLE_CHARACTER)
    suspend fun getSingleCharacter(@Path("id") id: Int) : Character

    @GET(GET_ALL_CONTINENTS)
    suspend fun getAllContinents(): List<Continent>

    @GET(GET_SINGLE_CONTINENT)
    suspend fun getSingleContinent(@Path("id") id: Int) : Continent
}