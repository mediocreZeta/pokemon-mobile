package com.zeta.pokemonapp.core.data.di

import androidx.room.Room
import com.zeta.pokemonapp.core.data.local.ILocalDataSource
import com.zeta.pokemonapp.core.data.local.LocalDataSource
import com.zeta.pokemonapp.core.data.local.PokemonDatabase
import com.zeta.pokemonapp.core.data.remote.IRemoteDataSource
import com.zeta.pokemonapp.core.data.remote.RemoteDataSource
import com.zeta.pokemonapp.core.data.remote.customapi.MyApiService
import com.zeta.pokemonapp.core.data.remote.pokemonapi.PokemonApiService
import com.zeta.pokemonapp.core.data.repositories.PokemonRepository
import com.zeta.pokemonapp.core.domain.repositories.IPokemonRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val myApiNetworkModule = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(MyApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(MyApiService::class.java)
    }


}
private val networkModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(PokemonApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(PokemonApiService::class.java)
    }
}

private val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            PokemonDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
    factory {
        get<PokemonDatabase>().myPokemonDao()
    }
}

private val repositoryModule = module {
    single<ILocalDataSource> {
        LocalDataSource(get())
    }
    single<IRemoteDataSource> {
        RemoteDataSource(get(), get())
    }
    single<IPokemonRepository> {
        PokemonRepository(get(), get())
    }
}

val coreModule = module {
    includes(
        myApiNetworkModule,
        networkModule,
        databaseModule,
        repositoryModule
    )
}

