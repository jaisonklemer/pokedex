package com.klemer.pokedexapp.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("results")
    val pokemons: MutableList<PokemonItem>,
)
