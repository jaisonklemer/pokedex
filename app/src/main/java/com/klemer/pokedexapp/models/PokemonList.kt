package com.klemer.pokedexapp.models

import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val nextPage: String,

    @SerializedName("results")
    val pokemons: List<PokemonListItem>
)

data class PokemonListItem(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)
