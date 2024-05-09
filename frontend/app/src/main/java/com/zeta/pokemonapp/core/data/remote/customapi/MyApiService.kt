package com.zeta.pokemonapp.core.data.remote.customapi

import com.zeta.pokemonapp.core.data.remote.customapi.response.ProbabilityResponse
import com.zeta.pokemonapp.core.data.remote.customapi.response.ReleaseResponse
import com.zeta.pokemonapp.core.data.remote.customapi.response.RenameResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiService {
    @GET("release")
    suspend fun getDecimalNumber(): ReleaseResponse

    @GET("catch")
    suspend fun getCatchProbability(): ProbabilityResponse

    @GET("rename")
    suspend fun getRenamePokemon(
        @Query("name") name: String,
        @Query("number") number: Int
    ): RenameResponse

    companion object {
        //TESTED IN EMULATOR
        private const val EMULATOR_LOCALHOST= "10.0.2.2"
        const val BASE_URL = "http://$EMULATOR_LOCALHOST:8100/pokemon/"
    }
}