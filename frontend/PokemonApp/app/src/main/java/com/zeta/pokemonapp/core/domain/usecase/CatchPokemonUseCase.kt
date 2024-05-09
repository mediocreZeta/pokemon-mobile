package com.zeta.pokemonapp.core.domain.usecase

import android.util.Log
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository

class CatchPokemonUseCase(private val repository: IPokemonRepository) {
    suspend operator fun invoke(): Boolean {
        Log.d("REPOSITORY", "hello world")
        return try {
            repository.catchPokemon()
        } catch (e: Exception) {
            false
        }
    }
}
