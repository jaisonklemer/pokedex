package com.klemer.pokedexapp.database.dao

import com.klemer.pokedexapp.models.PokemonItem
import androidx.room.*
import com.klemer.pokedexapp.models.Types


@Dao
interface PokemonDAO {

    @Query("SELECT * FROM PokemonItem")
    fun all(): MutableList<PokemonItem>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonItem: PokemonItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertType(types: List<Types>)

//    @Query("SELECT * FROM PokemonItem ORDER BY ID DESC LIMIT 1")
//    fun getLast(): String

}