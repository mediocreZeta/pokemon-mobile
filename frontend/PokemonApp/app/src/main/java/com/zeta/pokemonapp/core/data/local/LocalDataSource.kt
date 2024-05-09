package com.zeta.pokemonapp.core.data.local

import com.zeta.pokemonapp.core.data.local.pokemon.dao.MyPokemonDao
import com.zeta.pokemonapp.core.data.local.pokemon.entities.MyPokemonEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getPokemonList(): Flow<List<MyPokemonEntity>>
    suspend fun insertPokemon(pokemonEntity: MyPokemonEntity)
    suspend fun deletePokemon(pokemonEntity: MyPokemonEntity)
    suspend fun updatePokemon(pokemonEntity: MyPokemonEntity)
}
class LocalDataSource(private val pokemonDao: MyPokemonDao) : ILocalDataSource{

    override fun getPokemonList(): Flow<List<MyPokemonEntity>> = pokemonDao.getMyPokemonList()
    override suspend fun insertPokemon(pokemonEntity: MyPokemonEntity) = pokemonDao.insert(pokemonEntity)
    override suspend fun deletePokemon(pokemonEntity: MyPokemonEntity) = pokemonDao.delete(pokemonEntity)
    override suspend fun updatePokemon(pokemonEntity: MyPokemonEntity) = pokemonDao.update(pokemonEntity)
}