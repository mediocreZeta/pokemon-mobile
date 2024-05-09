package com.zeta.pokemonapp.util

sealed class UiState<out T>(val output: T?, val message: String) {
    data class Success<T>(val data: T) : UiState<T>(data, "success")
    data object Loading : UiState<Nothing>(null, message = "Loading")
    data object Empty : UiState<Nothing>(null, "Details not found")

    data class Error(val errorMessage: String) : UiState<Nothing>(null, errorMessage)


}