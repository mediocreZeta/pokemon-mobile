package com.zeta.pokemonapp.core.domain.usecase

import androidx.paging.PagingData
import com.zeta.pokemonapp.core.domain.model.PokemonModel
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(private val repository: IPokemonRepository) {
    operator fun invoke(): Flow<PagingData<PokemonModel>> {
        return repository.getPokemonList()
    }
}