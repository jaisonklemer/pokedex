package com.klemer.pokedexapp.endpoints

import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexEndpoint {

    @GET("pokemon")
    fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonList>

    @GET("pokemon/{id}")
    fun getPokemonInfo(@Path("id") id: String): Call<PokemonItem>
}