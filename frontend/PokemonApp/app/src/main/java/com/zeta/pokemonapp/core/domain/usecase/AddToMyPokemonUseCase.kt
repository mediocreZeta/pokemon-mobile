package com.zeta.pokemonapp.core.domain.usecase

import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddToMyPokemonUseCase(private val repository: IPokemonRepository) {
    suspend operator fun invoke(
        pokemonWithDetailsMode: PokemonWithDetailsModel
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                repository.addToMyPokemon(pokemonWithDetailsMode)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}
