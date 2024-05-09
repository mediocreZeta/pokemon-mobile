package com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails

import com.google.gson.annotations.SerializedName
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.artwork.Sprites
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.move.Move
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.move.Moves
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.type.Type
import com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.type.Types

data class PokemonDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("types") val types: List<Type>,
    @SerializedName("sprites") val image: Sprites,
    @SerializedName("moves") val moves: List<Move>,
)