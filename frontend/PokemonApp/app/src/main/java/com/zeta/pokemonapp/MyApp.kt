package com.zeta.pokemonapp

import android.app.Application
import com.zeta.pokemonapp.core.data.di.coreModule
import com.zeta.pokemonapp.core.domain.di.useCaseModule
import com.zeta.pokemonapp.page.mypokemon.myPokemonModule
import com.zeta.pokemonapp.page.home.homePokemonModule
import com.zeta.pokemonapp.page.pokemondetail.pokemonDetailsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                coreModule,
                useCaseModule,
                homePokemonModule,
                pokemonDetailsModule,
                myPokemonModule
            )
        }
    }
}