package com.zeta.pokemonapp.core.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zeta.pokemonapp.core.data.local.ILocalDataSource
import com.zeta.pokemonapp.core.data.local.pokemon.entities.toPokemonModel
import com.zeta.pokemonapp.core.data.remote.IRemoteDataSource
import com.zeta.pokemonapp.core.data.remote.ResultPagingSource
import com.zeta.pokemonapp.core.data.remote.pokemonapi.PokemonApiService
import com.zeta.pokemonapp.core.domain.model.ApiResponse
import com.zeta.pokemonapp.core.domain.model.PokemonModel
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.model.toPokemonEntity
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository
import com.zeta.pokemonapp.core.util.Util.toPokemonWithDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IPokemonRepository {
    private val cachedPokemonList = mutableMapOf<String, PokemonWithDetailsModel>()

    override suspend fun catchPokemon(): Boolean {
        val response = remoteDataSource.getCatchProbability()
        return when (response) {
            is ApiResponse.Success -> response.data
            is ApiResponse.Error -> throw response.exception
            else -> false
        }
    }

    override fun getMyPokemonList(): Flow<List<PokemonWithDetailsModel>> {
        return localDataSource.getPokemonList().map { pokemonEntities ->
            pokemonEntities.map {
                it.toPokemonModel()
            }
        }
    }

    override suspend fun searchPokemon(query: String): ApiResponse<PokemonWithDetailsModel> {
        val isNumber = query.toIntOrNull()
        val cachedValue = cachedPokemonList[query]

        if (isNumber != null) {
            val inDatabase = localDataSource.getPokemonList().first().find { it.id == isNumber }
            if (inDatabase != null) return ApiResponse.Success(inDatabase.toPokemonModel())
        }
        if (cachedValue != null) return ApiResponse.Success(cachedValue)

        val response = remoteDataSource.getPokemonBySearch(query)
        return when (response) {
            is ApiResponse.Empty -> ApiResponse.Empty
            is ApiResponse.Error -> ApiResponse.Error(response.exception)
            is ApiResponse.Success -> {
                val output = response.data.toPokemonWithDetailModel()
                cachedPokemonList[output.id.toString()] = output
                ApiResponse.Success(output)
            }
        }
    }

    override fun getPokemonList(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PokemonApiService.MAX_RESULT
            ),
            pagingSourceFactory = {
                ResultPagingSource(remoteDataSource)
            }
        ).flow
    }

    override suspend fun releasePokemon(pokemonWithDetailsModel: PokemonWithDetailsModel) {
        val pokemonEntity = pokemonWithDetailsModel.toPokemonEntity()
        localDataSource.deletePokemon(pokemonEntity)
    }

    override suspend fun renamePokemon(pokemonWithDetailsModel: PokemonWithDetailsModel) {
        val pokemonEntity = pokemonWithDetailsModel.toPokemonEntity()
        localDataSource.updatePokemon(pokemonEntity)
    }

    override suspend fun addToMyPokemon(pokemonWithDetailsModel: PokemonWithDetailsModel) {
        val pokemonEntity = pokemonWithDetailsModel.toPokemonEntity()
        localDataSource.insertPokemon(pokemonEntity)
    }

    override suspend fun getPrimeNumber(): Int {
        val response = remoteDataSource.getDecimalNumber()
        return when (response) {
            is ApiResponse.Success -> response.data
            is ApiResponse.Error -> throw response.exception
            else -> 0
        }
    }

    override suspend fun getRenamePokemon(name: String, num: Int): String {
        val response  = remoteDataSource.getRenamePokemon(name, num)
        return when(response) {
            is ApiResponse.Success -> response.data
            is ApiResponse.Error -> throw response.exception
            else -> throw IllegalArgumentException("Argument not valid")
        }
    }
}