package com.zeta.pokemonapp.core.util

import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.PokemonDetailsResponse
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemonlist.Result
import com.zeta.pokemonapp.core.domain.model.PokemonModel
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel

object Util {
    fun PokemonDetailsResponse.toPokemonWithDetailModel(): PokemonWithDetailsModel {
        val moves = moves.map { it.move.name }
        val image = image.other.officialArtwork.frontDefault
        val types = types.map { it.type.name }

        return PokemonWithDetailsModel(
            id,
            name,
            image,
            types,
            moves
        )
    }

    fun Result.toPokemonModel(): PokemonModel{
        val id = getId()
        val image = getPokemonImage(id)
        return PokemonModel(
            name,
            id,
            image
        )
    }

    private fun getPokemonImage(id: String): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
    }
}