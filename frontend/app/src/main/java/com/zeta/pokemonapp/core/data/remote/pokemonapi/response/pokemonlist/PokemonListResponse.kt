package com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemonlist

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>?
)