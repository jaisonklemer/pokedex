package com.klemer.pokedexapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.pokedexapp.models.PokemonList
import com.klemer.pokedexapp.repository.PokedexRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _pokemons = MutableLiveData<PokemonList>()
    val pokemons: LiveData<PokemonList> = _pokemons

    private val repository = PokedexRepository()

    fun getPokemons() {
        repository.getPokemons {
            _pokemons.value = it
        }
    }


}