package com.klemer.pokedexapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.repository.PokedexRepository

class PokemonDetailViewModel : ViewModel() {

    val pokemonDetailLive = MutableLiveData<PokemonItem>()

    private val repository = PokedexRepository()

    fun getPokemonDetail(pokemonId: String) {
        repository.getSpecificPokemon(pokemonId) { pokemon, error ->
            if (pokemon != null) {
                pokemonDetailLive.value = pokemon
            }
        }
    }
}