package com.zeta.pokemonapp.core.data.local.pokemon.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel

@Entity(tableName = "my_pokemon")
data class MyPokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val types: List<String>,
    val moveNames: List<String>,
    val countRename: Int = 0
)

fun MyPokemonEntity.toPokemonModel(): PokemonWithDetailsModel{
    return PokemonWithDetailsModel(
        id, name, image, types, moveNames, countRename
    )
}
