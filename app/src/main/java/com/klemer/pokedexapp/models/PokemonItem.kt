package com.klemer.pokedexapp.models

import com.google.gson.annotations.SerializedName

data class PokemonItem(
    @SerializedName("types")
    val types: List<Types>
)

data class Types(
    @SerializedName("slot")
    val slot: String,

    @SerializedName("type")
    val type: PokemonType
)

data class PokemonType(
    @SerializedName("name")
    val typeName: String,

    @SerializedName("url")
    val typeUrl: String
)