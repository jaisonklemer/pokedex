package com.klemer.pokedexapp.repository

import android.content.Context
import com.klemer.pokedexapp.database.AppDatabase
import com.klemer.pokedexapp.endpoints.PokedexEndpoint
import com.klemer.pokedexapp.models.PokemonDetails
import com.klemer.pokedexapp.models.PokemonFromGeneration
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonResponse
import com.klemer.pokedexapp.services.RetrofitService
import com.klemer.pokedexapp.singletons.APICount
import kotlinx.coroutines.*

class PokedexRepository {

    private lateinit var appContext: Context

    private val API = RetrofitService()
        .getInstance("https://pokeapi.co/").create(PokedexEndpoint::class.java)

    fun getPokemons(context: Context, callback: (PokemonResponse?, String?) -> Unit) {
        appContext = context

        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getPokemons(APICount.resultCount, APICount.offsetCount)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
//                        insertIntoDatabase(response.body()!!.pokemons)
                        callback(it, null)
                    }
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

    fun insertIntoDatabase(items: List<PokemonItem>) {
        val dao = AppDatabase.getDatabase(appContext).pokemonDAO()
        items.forEach { poke ->
            val details = PokemonDetails(pokemon = poke, types = poke.types)
            dao.insertDetails(details)
        }
    }

    fun fetchAllFromDatabase(context: Context): MutableList<PokemonItem>? {
        appContext = context
        val dao = AppDatabase.getDatabase(appContext).pokemonDAO()
        return dao.all()
    }
}