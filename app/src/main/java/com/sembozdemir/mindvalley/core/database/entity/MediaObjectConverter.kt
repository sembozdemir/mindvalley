package com.sembozdemir.mindvalley.core.database.entity

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MediaObjectConverter {

    private val adapter by lazy {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, MediaObject::class.java)
        val adapter: JsonAdapter<List<MediaObject>> = moshi.adapter(type)
        adapter
    }

    @TypeConverter
    fun toJson(mediaList: List<MediaObject>): String {
        return adapter.toJson(mediaList).orEmpty()
    }

    @TypeConverter
    fun fromJson(mediaJson: String): List<MediaObject> {
        return adapter.fromJson(mediaJson).orEmpty()
    }
}