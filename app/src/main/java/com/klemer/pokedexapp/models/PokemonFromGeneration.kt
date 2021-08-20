package com.klemer.pokedexapp.models

import com.google.gson.annotations.SerializedName

data class PokemonFromGeneration(
    @SerializedName("pokemon_species")
    val pokemon: MutableList<PokemonListItem>,
)
