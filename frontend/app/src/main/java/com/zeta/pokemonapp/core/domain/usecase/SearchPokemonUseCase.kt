package com.zeta.pokemonapp.core.domain.usecase

import com.zeta.pokemonapp.core.domain.model.ApiResponse
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchPokemonUseCase(private val repository: IPokemonRepository) {
    suspend operator fun invoke(query: String): PokemonWithDetailsModel? {
        return withContext(Dispatchers.IO) {
            val response = repository.searchPokemon(query)
            when (response) {
                is ApiResponse.Error -> throw response.exception
                is ApiResponse.Empty -> null
                is ApiResponse.Success -> response.data
            }
        }
    }
}