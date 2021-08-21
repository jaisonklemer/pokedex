package com.klemer.pokedexapp.endpoints

import com.klemer.pokedexapp.models.PokemonFromGeneration
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexEndpoint {

    @GET("api/v2/pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonList>

    @GET("api/v2/pokemon/{id}")
   suspend fun getPokemonInfo(@Path("id") id: String): Response<PokemonItem>

    @GET("api/v2/generation/{id}")
    suspend fun getPokemonFromGeneration(@Path("id") id: String): Response<PokemonFromGeneration>
}