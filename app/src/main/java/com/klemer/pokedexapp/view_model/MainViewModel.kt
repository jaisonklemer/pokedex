package com.klemer.pokedexapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonResponse
import com.klemer.pokedexapp.repository.PokedexRepository

class MainViewModel : ViewModel() {

    val primaryPokemonList = MutableLiveData<PokemonResponse>()

    val finalPokemonsList = MutableLiveData<PokemonResponse?>()

    private val repository = PokedexRepository()

    fun getPokemons() {
        repository.getPokemons { result, error ->
            if (result != null) {
                primaryPokemonList.value = result
            }

        }
    }

    fun treatPokemonList(list: PokemonResponse) {
        var count = 0
        for (pokemon in list.pokemons) {
            repository.getSpecificPokemon(pokemon.getIdFromUrl()) { pokemonResult, error ->
                if (pokemonResult != null) {
                    pokemon.types = pokemonResult.types
                    pokemon.id = pokemonResult.id
                    count++

                    if (count == list.pokemons.size) {
                        finalPokemonsList.value = list
                    }
                }
            }
        }
    }

    fun searchPokemon(idOrName: String, callback: (PokemonItem?, String?) -> Unit) {
        repository.getSpecificPokemon(idOrName) { pokemon, error ->
            if (pokemon != null) {
                val pokemonItem = mutableListOf<PokemonItem>()
                pokemonItem.add(pokemon)
                finalPokemonsList.value = PokemonResponse(pokemonItem)
            }

            callback(pokemon, error)
        }

    }

    fun getPokemonFromGeneration(idOrName: String, callback: () -> Unit) {
        repository.getPokemonFromGeneration(idOrName) {
            primaryPokemonList.value = PokemonResponse(it.pokemon)
            callback()
        }
    }

    fun clearPokemonList() {
        finalPokemonsList.value = null
    }
}