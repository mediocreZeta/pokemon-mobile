package com.zeta.pokemonapp.page.mypokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.usecase.GetMyPokemonUseCase
import com.zeta.pokemonapp.util.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MyPokemonViewModel(
    getMyPokemonUseCase: GetMyPokemonUseCase
) : ViewModel() {
    val myPokemonList: StateFlow<UiState<List<PokemonWithDetailsModel>>> = getMyPokemonUseCase()
        .map { pokemonList ->
            if (pokemonList.isEmpty()) UiState.Empty
            else UiState.Success(pokemonList)
        }
        .catch {
            UiState.Error(it.localizedMessage ?: "Error")
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiState.Loading
        )


}