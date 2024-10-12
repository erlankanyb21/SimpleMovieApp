package com.erkan.simplemovieapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

//    @TypeConverter
//    fun fromOrigin(value: String?) = fromJson<OriginDto>(value)
//
//    @TypeConverter
//    fun toOrigin(origin: OriginDto): String = toJson(origin)

    private inline fun <reified T> typeToken() = object : TypeToken<T>() {}.type

    private inline fun <reified T> fromJson(value: String?): T =
        Gson().fromJson(value, typeToken<T>())

    private inline fun <reified T> toJson(generic: T) =
        Gson().toJson(generic, typeToken<T>())
}