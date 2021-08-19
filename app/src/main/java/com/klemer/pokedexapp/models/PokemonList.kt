package com.klemer.pokedexapp.models

import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val nextPage: String,

    @SerializedName("results")
    val pokemons: MutableList<PokemonListItem>,
)

data class PokemonListItem(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,

    var id: Int,

    var types: List<Types>,
) {
    companion object {
        fun fromPokemonItem(pokemon: PokemonItem): PokemonListItem {
            return PokemonListItem(
                name = pokemon.name,
                url = "",
                id = pokemon.id,
                types = pokemon.types
            )
        }
    }

}
