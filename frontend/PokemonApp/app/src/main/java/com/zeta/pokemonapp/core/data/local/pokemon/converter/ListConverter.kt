package com.zeta.pokemonapp.core.data.local.pokemon.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    @TypeConverter
    fun fromList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return try {
            val listType = object : TypeToken<List<String>>() {}.type
            val outputList: List<String> = Gson().fromJson(value, listType)
            outputList
        } catch (e: Exception) {
            arrayListOf<String>()
        }
    }
}