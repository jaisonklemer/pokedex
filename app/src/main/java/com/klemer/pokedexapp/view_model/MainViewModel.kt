package com.klemer.pokedexapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.pokedexapp.models.PokemonList
import com.klemer.pokedexapp.models.PokemonListItem
import com.klemer.pokedexapp.repository.PokedexRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _pokemons = MutableLiveData<PokemonList>()
    val pokemons: LiveData<PokemonList> = _pokemons

    private val _pokemonsList = MutableLiveData<PokemonList>()
    val pokemonsList: LiveData<PokemonList> = _pokemonsList

    private val repository = PokedexRepository()

    fun getPokemons() {
        repository.getPokemons {
            _pokemons.value = it
        }
    }

    fun treatPokemonList(list: PokemonList) {
        var count = 0;
        for (poke in list.pokemons) {
            repository.getSpecificPokemon(getPokemonId(poke)) {
                poke.types = it.types
                count++

                if (count == list.pokemons.size) {
                    _pokemonsList.value = list
                }
            }

        }

    }


    private fun getPokemonId(pokemon: PokemonListItem): String {
        val list = pokemon.url.split("/")
        val size = list.size
        return list[size - 2]
    }


}