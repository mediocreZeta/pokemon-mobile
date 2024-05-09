package com.zeta.pokemonapp.core.data.remote.pokemonapi

import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.PokemonDetailsResponse
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemonlist.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int = MAX_RESULT
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: String
    ): PokemonDetailsResponse

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
        const val MAX_RESULT = 20
    }
}