package com.sembozdemir.mindvalley.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "category")
data class CategoriesEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = CACHED,

    @TypeConverters(StringListConverter::class)
    @ColumnInfo(name = "categories")
    var categories: List<String> = emptyList()
) {
    companion object {
        const val CACHED = "cached"
    }
}