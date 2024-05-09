package com.zeta.pokemonapp.core.domain.usecase

import android.util.Log
import androidx.paging.LOGGER
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository

class RenamePokemonUseCase(private val repository: IPokemonRepository) {
    suspend operator fun invoke(pokemonWithDetailsModel: PokemonWithDetailsModel): Boolean {
        return try {
            val countRename = pokemonWithDetailsModel.countRename
            val name = pokemonWithDetailsModel.name
            val newName = repository.getRenamePokemon(
                name,
                countRename
            )
            val newPokemon = pokemonWithDetailsModel.copy(
                name = newName,
                countRename = countRename + 1
            )

            repository.renamePokemon(newPokemon)
            true
        } catch (e: Exception) {
            false
        }
    }
}