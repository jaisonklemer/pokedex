package com.klemer.pokedexapp.models

import com.google.gson.annotations.SerializedName

data class PokemonItem(
    @SerializedName("name")
    val name: String,

    @SerializedName("types")
    val types: List<Types>,

    @SerializedName("id")
    val id: Int,
)

data class Types(
    @SerializedName("slot")
    val slot: String,

    @SerializedName("type")
    val type: PokemonType,
)

data class PokemonType(
    @SerializedName("name")
    val typeName: String,

    @SerializedName("url")
    val typeUrl: String,
)