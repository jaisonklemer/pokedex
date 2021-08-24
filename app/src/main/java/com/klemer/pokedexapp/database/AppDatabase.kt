package com.klemer.pokedexapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.klemer.pokedexapp.database.dao.PokemonDAO
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.models.PokemonType
import com.klemer.pokedexapp.models.Types

@Database(entities = [PokemonItem::class, Types::class, PokemonType::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDAO(): PokemonDAO

    companion object {

        /**
         * Singleton que irá gerar nossa classe AppDatabse com tudo implementado pelo Room.
         */
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "pokemon_app_db"
            )
                .allowMainThreadQueries() // Utilizar esta linha quando NÃO utilizar couroutines
                .build()
        }
    }
}