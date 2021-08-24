package com.klemer.pokedexapp.database.dao

import com.klemer.pokedexapp.models.PokemonItem
import android.provider.SyncStateContract.Helpers.insert
import androidx.room.*
import com.klemer.pokedexapp.models.PokemonDetails
import com.klemer.pokedexapp.models.Types


@Dao
interface PokemonDAO {

    @Query("SELECT * FROM PokemonItem")
    fun all(): MutableList<PokemonItem>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonItem: PokemonItem) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertType(types: List<Types>)

    @Transaction
    fun insertTransaction(pokemonItem: PokemonItem) {
        insert(pokemonItem)
        insertType(pokemonItem.types)
    }

    @Transaction
    fun insertDetails(pokemonDetails: PokemonDetails) {

        val pokemonId = insert(pokemonDetails.pokemon)

        insertType(pokemonDetails.types)
    }
}