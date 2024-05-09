package com.zeta.pokemonapp.page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zeta.pokemonapp.core.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomePokemonViewModel(
    getPokemonListUseCase: GetPokemonListUseCase,
) : ViewModel() {
    val paginationFlow = getPokemonListUseCase()
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )
}