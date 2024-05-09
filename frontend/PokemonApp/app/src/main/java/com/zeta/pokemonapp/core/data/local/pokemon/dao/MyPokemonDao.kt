package com.zeta.pokemonapp.core.data.local.pokemon.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zeta.pokemonapp.core.data.local.pokemon.entities.MyPokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyPokemonDao {
    @Insert
    suspend fun insert(myPokemonEntity: MyPokemonEntity)

    @Update
    suspend fun update(myPokemonEntity: MyPokemonEntity)

    @Delete
    suspend fun delete(myPokemonEntity: MyPokemonEntity)

    @Query("SELECT * FROM my_pokemon")
    fun getMyPokemonList(): Flow<List<MyPokemonEntity>>
}