package com.zeta.pokemonapp.core.domain.di

import com.zeta.pokemonapp.core.domain.usecase.AddToMyPokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.CatchPokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.GetMyPokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.GetPokemonListUseCase
import com.zeta.pokemonapp.core.domain.usecase.ReleasePokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.RenamePokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.SearchPokemonUseCase
import org.koin.dsl.module

private val addToMyPokemonUseCaseModule = module {
    factory { AddToMyPokemonUseCase(get()) }
}
private val getMyPokemonUseCaseModule = module {
    factory { GetMyPokemonUseCase(get()) }
}
private val getPokemonListUseCaseModule = module {
    factory { GetPokemonListUseCase(get()) }
}
private val releasePokemonUseCaseModule = module {
    factory { ReleasePokemonUseCase(get()) }
}
private val renamePokemonUseCaseModule = module {
    factory { RenamePokemonUseCase(get()) }
}
private val searchPokemonUseCaseModule = module {
    factory { SearchPokemonUseCase(get()) }
}

private val catchPokemonUseCase = module {
    factory { CatchPokemonUseCase(get()) }
}

val useCaseModule = module {
    includes(
        addToMyPokemonUseCaseModule,
        getMyPokemonUseCaseModule,
        getPokemonListUseCaseModule,
        releasePokemonUseCaseModule,
        renamePokemonUseCaseModule,
        searchPokemonUseCaseModule,
        catchPokemonUseCase
    )
}
