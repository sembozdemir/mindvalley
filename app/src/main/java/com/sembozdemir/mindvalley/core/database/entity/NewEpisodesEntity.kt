package com.sembozdemir.mindvalley.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "newEpisodes")
data class NewEpisodesEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = CACHED,

    @TypeConverters(MediaObjectConverter::class)
    @ColumnInfo(name = "mediaObjects")
    var mediaObjects: List<MediaObject> = emptyList()
) {
    companion object {
        const val CACHED = "cached"
    }
}