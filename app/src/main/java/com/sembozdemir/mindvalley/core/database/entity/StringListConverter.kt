package com.sembozdemir.mindvalley.core.database.entity

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


class StringListConverter {

    private val adapter by lazy {
        val moshi: Moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        adapter
    }

    @TypeConverter
    fun toJson(stringList: List<String>): String {
        return adapter.toJson(stringList).orEmpty()
    }

    @TypeConverter
    fun fromJson(stringJson: String): List<String> {
        return adapter.fromJson(stringJson).orEmpty()
    }
}