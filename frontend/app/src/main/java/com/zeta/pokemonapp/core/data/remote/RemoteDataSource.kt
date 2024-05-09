package com.zeta.pokemonapp.core.data.remote

import com.zeta.pokemonapp.core.data.remote.customapi.MyApiService
import com.zeta.pokemonapp.core.data.remote.pokemonapi.PokemonApiService
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.PokemonDetailsResponse
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemonlist.Result
import com.zeta.pokemonapp.core.domain.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface IRemoteDataSource {
    suspend fun getPokemonList(
        page: Int?
    ): Flow<ApiResponse<PokemonListAndToken>>

    suspend fun getPokemonBySearch(
        nameOrId: String
    ): ApiResponse<PokemonDetailsResponse>

    suspend fun getCatchProbability(): ApiResponse<Boolean>
    suspend fun getDecimalNumber(): ApiResponse<Int>
    suspend fun getRenamePokemon(name: String, num: Int): ApiResponse<String>
}

data class PokemonListAndToken(
    val pokemon: List<Result>,
    val nextToken: String?
)

class RemoteDataSource(
    private val pokemonApiService: PokemonApiService,
    private val myApiService: MyApiService
) : IRemoteDataSource {
    override suspend fun getPokemonList(page: Int?): Flow<ApiResponse<PokemonListAndToken>> {
        return flow {
            try {
                val output = handleInput(pokemonApiService, page)
                emit(output)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPokemonBySearch(nameOrId: String): ApiResponse<PokemonDetailsResponse> {
        return try {
            val pokemonDetails = pokemonApiService.getPokemonDetails(nameOrId)
            ApiResponse.Success(pokemonDetails)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun getCatchProbability(): ApiResponse<Boolean> {
        return try {
            val isSuccess = myApiService.getCatchProbability().isSuccess
            ApiResponse.Success(isSuccess)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun getDecimalNumber(): ApiResponse<Int> {
        return try {
            val decimalNumber = myApiService.getDecimalNumber().number
            ApiResponse.Success(decimalNumber)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun getRenamePokemon(name: String, num: Int): ApiResponse<String> {
        return try {
            val newName = myApiService.getRenamePokemon(name, num).name
            ApiResponse.Success(newName)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    private suspend fun handleInput(
        pokemonApiService: PokemonApiService,
        page: Int?
    ): ApiResponse<PokemonListAndToken> {
        val pokemonResponse = pokemonApiService.getPokemonList(page)
        val nextPageToken = pokemonResponse.next
        val pokemonList = pokemonResponse.results ?: return ApiResponse.Empty
        if (pokemonList.isEmpty()) return ApiResponse.Empty

        val pokemonListAndToken = PokemonListAndToken(
            pokemonList,
            nextPageToken
        )
        return ApiResponse.Success(pokemonListAndToken)
    }
}

