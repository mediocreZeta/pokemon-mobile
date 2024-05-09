package com.zeta.pokemonapp.core.domain.usecase

import android.util.Log
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReleasePokemonUseCase(private val repository: IPokemonRepository) {
    suspend operator fun invoke(pokemonWithDetailsModel: PokemonWithDetailsModel): Boolean {

        return try {
            val getPrimeNumber = repository.getPrimeNumber()
            val isPrimeNumber = checkPrimeNumber(getPrimeNumber)
            if (isPrimeNumber) {
                withContext(Dispatchers.IO) {
                    repository.releasePokemon(pokemonWithDetailsModel)
                }
            }
            isPrimeNumber
        } catch (e: Exception) {
            Log.d("HELLO", "error")

            false
        }

    }

    private fun checkPrimeNumber(num: Int): Boolean {
        val halfNumber = num / 2
        for (i in 2..halfNumber) {
            if (num % i == 0) return false
        }
        return true
    }
}