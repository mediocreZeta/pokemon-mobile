package com.zeta.pokemonapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zeta.pokemonapp.core.data.local.pokemon.converter.ListConverter
import com.zeta.pokemonapp.core.data.local.pokemon.dao.MyPokemonDao
import com.zeta.pokemonapp.core.data.local.pokemon.entities.MyPokemonEntity

@Database(
    entities = [MyPokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun myPokemonDao(): MyPokemonDao

    companion object {
        const val DATABASE_NAME = "mypokemon-db"
    }

}