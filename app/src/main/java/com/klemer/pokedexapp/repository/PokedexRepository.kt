package com.klemer.pokedexapp.repository

import com.klemer.pokedexapp.endpoints.PokedexEndpoint
import com.klemer.pokedexapp.extensions.setPropertyValue
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonList
import com.klemer.pokedexapp.models.PokemonListItem
import com.klemer.pokedexapp.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KMutableProperty1

class PokedexRepository {

    private val API = RetrofitService()
        .getInstance("https://pokeapi.co/api/v2/").create(PokedexEndpoint::class.java)

    fun getPokemons(callback: (PokemonList) -> Unit) {

        API.getPokemons(50, 0).enqueue(object : Callback<PokemonList> {

            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                response.body()?.let { pokemonList ->
                    callback(pokemonList)
                }
            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getSpecificPokemon(id: String, callback: (PokemonItem) -> Unit) {
        API.getPokemonInfo(id).enqueue(object : Callback<PokemonItem> {

            override fun onResponse(call: Call<PokemonItem>, response: Response<PokemonItem>) {
                response.body()?.let { callback(it) }
            }

            override fun onFailure(call: Call<PokemonItem>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}