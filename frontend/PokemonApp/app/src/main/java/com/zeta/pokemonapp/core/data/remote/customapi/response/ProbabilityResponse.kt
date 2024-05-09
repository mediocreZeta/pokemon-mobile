package com.zeta.pokemonapp.core.data.remote.customapi.response

import com.google.gson.annotations.SerializedName

data class ProbabilityResponse(
    @SerializedName("is_success") val isSuccess: Boolean
)