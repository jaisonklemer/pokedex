package com.klemer.pokedexapp.models

import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity
data class PokemonItem(
    @SerializedName("id")
    var id: String,

    @PrimaryKey
    @ColumnInfo(name = "pokemon_name")
    @SerializedName("name")
    val name: String,

    @SerializedName("types")
    var types: List<Types>,

    @ColumnInfo(name = "pokemon_url")
    @SerializedName("url")
    val url: String,
) {

    fun getIdFromUrl(): String {
        return url.split("/")[6]
    }
}

@Entity
data class Types(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "types_pokemon_id")
    val id: Int,

    @SerializedName("slot")
    val slot: String,

    @Embedded
    @SerializedName("type")
    val type: PokemonType,
)

@Entity
data class PokemonType(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pokemon_type_name")
    val id: Int,

    @SerializedName("name")
    val typeName: String,

    @ColumnInfo(name = "pokemon_type_url")
    @SerializedName("url")
    val typeUrl: String,
)