package com.zeta.pokemonapp.core.domain.model

import android.os.Parcelable
import com.zeta.pokemonapp.core.data.local.pokemon.entities.MyPokemonEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonWithDetailsModel(
    val id: Int,
    val name: String,
    val image: String,
    val types: List<String>,
    val moveNames: List<String>,
    val countRename: Int = 0
) : Parcelable

fun PokemonWithDetailsModel.toPokemonEntity(): MyPokemonEntity {
    return MyPokemonEntity(
        id,
        name,
        image,
        types,
        moveNames,
        countRename
    )
}
