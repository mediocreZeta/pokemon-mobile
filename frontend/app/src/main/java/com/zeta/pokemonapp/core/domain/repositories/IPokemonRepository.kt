package com.zeta.pokemonapp.core.domain.repositories

import androidx.paging.PagingData
import com.zeta.pokemonapp.core.domain.model.ApiResponse
import com.zeta.pokemonapp.core.domain.model.PokemonModel
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    fun getPokemonList(): Flow<PagingData<PokemonModel>>
    fun getMyPokemonList(): Flow<List<PokemonWithDetailsModel>>

    suspend fun catchPokemon(): Boolean
    suspend fun searchPokemon(query: String): ApiResponse<PokemonWithDetailsModel>
    suspend fun releasePokemon(pokemonWithDetailsModel: PokemonWithDetailsModel)
    suspend fun renamePokemon(pokemonWithDetailsModel: PokemonWithDetailsModel)
    suspend fun addToMyPokemon(pokemonWithDetailsModel: PokemonWithDetailsModel)

    suspend fun getPrimeNumber (): Int

    suspend fun getRenamePokemon(name: String, num: Int): String
}