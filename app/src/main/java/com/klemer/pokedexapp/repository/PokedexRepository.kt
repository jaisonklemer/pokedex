package com.klemer.pokedexapp.repository

import com.klemer.pokedexapp.endpoints.PokedexEndpoint
import com.klemer.pokedexapp.models.PokemonList
import com.klemer.pokedexapp.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokedexRepository {

    private val API = RetrofitService()
        .getInstance("https://pokeapi.co/api/v2/").create(PokedexEndpoint::class.java)

    fun getPokemons(callback: (PokemonList) -> Unit) {

        API.getPokemons(100, 0).enqueue(object : Callback<PokemonList> {
            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                response.body()?.let {
                    callback(it)
                }
            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}