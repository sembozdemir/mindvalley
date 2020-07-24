package com.sembozdemir.mindvalley.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "channel")
data class ChannelEntity(

    @PrimaryKey
    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "count")
    var count: Int = 0,

    @ColumnInfo(name = "isSeries")
    var isSeries: Boolean = false,

    @ColumnInfo(name = "iconImageUrl")
    var iconImageUrl: String = "",

    @TypeConverters(MediaObjectConverter::class)
    @ColumnInfo(name = "mediaObjects")
    var mediaObjects: List<MediaObject> = emptyList()
)