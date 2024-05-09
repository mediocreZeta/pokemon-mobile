package com.zeta.pokemonapp.core.data.remote.pokemonapi.response.pokemonlist

data class Result(
    val name: String,
    val url: String
) {
    fun getId(): String {
        val pattern = """/(\d+)/?$""".toRegex()
        val match = pattern.find(url)
        val pokemonNumber = match?.value?.substringBeforeLast("/") ?: "0"
        return pokemonNumber.replace("/", "")
    }

}