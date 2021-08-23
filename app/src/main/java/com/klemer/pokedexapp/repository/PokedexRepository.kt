package com.klemer.pokedexapp.repository

import com.klemer.pokedexapp.endpoints.PokedexEndpoint
import com.klemer.pokedexapp.models.PokemonFromGeneration
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonResponse
import com.klemer.pokedexapp.services.RetrofitService
import com.klemer.pokedexapp.singletons.APICount
import kotlinx.coroutines.*

class PokedexRepository {

    private val API = RetrofitService()
        .getInstance("https://pokeapi.co/").create(PokedexEndpoint::class.java)

    fun getPokemons(callback: (PokemonResponse?, String?) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getPokemons(APICount.resultCount, APICount.offsetCount)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { callback(it, null) }
                    APICount.offsetCount += APICount.resultCount
                } else {
                    callback(null, response.errorBody().toString())
                }
            }
        }
    }

    fun getSpecificPokemon(idOrName: String, callback: (PokemonItem?, String?) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getPokemonInfo(idOrName)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, null)
                }
            }
        }
    }

    fun getPokemonFromGeneration(genNameOrId: String, callback: (PokemonFromGeneration) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getPokemonFromGeneration(genNameOrId)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { callback(it) }
                }
            }
        }
    }
}