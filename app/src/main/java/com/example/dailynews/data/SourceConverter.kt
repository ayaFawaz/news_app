package com.example.dailynews.data

import androidx.room.TypeConverter
import com.example.dailynews.data.models.Source
import com.google.gson.Gson

class SourceConverter {

    @TypeConverter
    fun fromSource(source: Source) : String {
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun toSource(string: String) : Source {
        val gson = Gson()
        return gson.fromJson(string, Source::class.java)
    }
}