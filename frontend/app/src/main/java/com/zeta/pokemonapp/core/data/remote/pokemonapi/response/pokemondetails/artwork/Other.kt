package com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemondetails.artwork

import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)