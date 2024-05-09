package com.zeta.pokemonapp.core.domain.usecase

import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository
import kotlinx.coroutines.flow.Flow

class GetMyPokemonUseCase(private val repository: IPokemonRepository) {
    operator fun invoke(): Flow<List<PokemonWithDetailsModel>> {
        return repository.getMyPokemonList()
    }
}
