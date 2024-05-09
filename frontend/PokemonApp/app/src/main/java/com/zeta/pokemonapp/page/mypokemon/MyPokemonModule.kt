package com.zeta.pokemonapp.page.mypokemon

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myPokemonModule = module {
    viewModel { MyPokemonViewModel(get()) }
}