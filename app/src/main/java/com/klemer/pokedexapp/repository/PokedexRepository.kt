package com.klemer.pokedexapp.repository

import com.klemer.pokedexapp.endpoints.PokedexEndpoint
import com.klemer.pokedexapp.models.PokemonFromGeneration
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonList
import com.klemer.pokedexapp.models.PokemonListItem
import com.klemer.pokedexapp.services.RetrofitService
import com.klemer.pokedexapp.singletons.APICount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KMutableProperty1

class PokedexRepository {

    private val API = RetrofitService()
        .getInstance("https://pokeapi.co/").create(PokedexEndpoint::class.java)

    fun getPokemons(callback: (PokemonList) -> Unit) {

        API.getPokemons(50, APICount.offsetCount).enqueue(object : Callback<PokemonList> {

            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                response.body()?.let { pokemonList ->
                    callback(pokemonList)
                    APICount.offsetCount += 50
                }
            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                println(t.localizedMessage)
            }
        })
    }

    fun getSpecificPokemon(idOrName: String, callback: (PokemonItem?, String?) -> Unit) {

        API.getPokemonInfo(idOrName).enqueue(object : Callback<PokemonItem> {

            override fun onResponse(call: Call<PokemonItem>, response: Response<PokemonItem>) {
                if (response.body() != null) {
                    callback(response.body(), null)
                } else {
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<PokemonItem>, t: Throwable) {
                callback(null, t.localizedMessage)
            }
        })
    }

    fun getPokemonFromGeneration(genNameOrId: String, callback: (PokemonFromGeneration) -> Unit) {
        API.getPokemonFromGeneration(genNameOrId).enqueue(object : Callback<PokemonFromGeneration> {
            override fun onResponse(
                call: Call<PokemonFromGeneration>,
                response: Response<PokemonFromGeneration>,
            ) {
                if (response.code() == 200) response.body()?.let { callback(it) }
            }

            override fun onFailure(call: Call<PokemonFromGeneration>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}