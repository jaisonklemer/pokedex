package com.klemer.pokedexapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonList
import com.klemer.pokedexapp.models.PokemonListItem
import com.klemer.pokedexapp.repository.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _pokemons = MutableLiveData<PokemonList>()
    val pokemons: LiveData<PokemonList> = _pokemons

    private val _finalPokemonsList = MutableLiveData<PokemonList?>()
    val finalPokemonsList: LiveData<PokemonList?> = _finalPokemonsList

    private val repository = PokedexRepository()

    fun getPokemons() {
        repository.getPokemons {
            _pokemons.value = it
        }
    }

    fun treatPokemonList(list: PokemonList) {
        var count = 0

        viewModelScope.launch(Dispatchers.Default) {

            for (poke in list.pokemons) {
                repository.getSpecificPokemon(getPokemonId(poke)) { pokemon, error ->
                    if (pokemon != null) {
                        poke.types = pokemon.types
                        poke.id = pokemon.id
                        count++

                        if (count == list.pokemons.size) {
                            _finalPokemonsList.value = list
                        }
                    }

                }
            }
        }
    }

    fun searchPokemon(idOrName: String, callback: (PokemonItem?, String?) -> Unit) {
        repository.getSpecificPokemon(idOrName) { pokemon, error ->
            if (pokemon != null) {
                val pokemonListItem = PokemonListItem.fromPokemonItem(pokemon)
                val listOf = mutableListOf<PokemonListItem>()
                listOf.add(pokemonListItem)

                val pokemonList = PokemonList(0, "", listOf)

                _finalPokemonsList.value = pokemonList
            }

            callback(pokemon, error)
        }

    }

    private fun getPokemonId(pokemon: PokemonListItem): String {
        val list = pokemon.url.split("/")
        val size = list.size
        return list[size - 2]
    }

    fun clearPokemonList() {
        _finalPokemonsList.value = null
    }
}