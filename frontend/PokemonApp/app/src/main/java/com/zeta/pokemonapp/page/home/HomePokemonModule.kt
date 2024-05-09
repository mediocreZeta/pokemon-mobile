package com.zeta.pokemonapp.page.home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val homePokemonViewModelModule = module {
    viewModel { HomePokemonViewModel(get()) }
}


val homePokemonModule = module {
    includes(
        homePokemonViewModelModule,
    )
}