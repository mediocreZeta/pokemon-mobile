package com.zeta.pokemonapp.page.pokemondetail

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokemonDetailsModule = module {
    viewModel {
        PokemonDetailsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}