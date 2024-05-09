package com.zeta.pokemonapp.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zeta.pokemonapp.core.data.remote.pokemonapi.PokemonApiService
import com.zeta.pokemonapp.core.domain.model.ApiResponse
import com.zeta.pokemonapp.core.domain.model.PokemonModel
import com.zeta.pokemonapp.core.util.Util.toPokemonModel
import kotlinx.coroutines.flow.first

class ResultPagingSource(
    private val remoteDataSource: IRemoteDataSource,
) : PagingSource<Int, PokemonModel>() {
    private val maxResult = PokemonApiService.MAX_RESULT
    override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(maxResult) ?: anchorPage?.nextKey?.minus(maxResult)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        val token = params.key ?: 0
        val response = remoteDataSource.getPokemonList(token).first()
        return try {
            when (response) {
                is ApiResponse.Error -> LoadResult.Error(response.exception)
                is ApiResponse.Empty -> LoadResult.Page(
                    data = listOf(),
                    prevKey = null,
                    nextKey = null
                )

                is ApiResponse.Success -> handleSuccessResponse(response.data, token)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun handleSuccessResponse(
        pokemonListAndToken: PokemonListAndToken,
        token: Int
    ): LoadResult<Int, PokemonModel> {
        val isNextTokenNull = pokemonListAndToken.nextToken == null
        var nextToken: Int? = null
        if (!isNextTokenNull) {
            nextToken = token.plus(maxResult)
        }
        val pokemonModels = pokemonListAndToken.pokemon.map { it.toPokemonModel() }
        return LoadResult.Page(
            data = pokemonModels,
            prevKey = null,
            nextKey = nextToken
        )
    }
}


