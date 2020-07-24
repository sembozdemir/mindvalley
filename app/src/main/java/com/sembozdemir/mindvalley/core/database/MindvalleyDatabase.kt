package com.sembozdemir.mindvalley.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sembozdemir.mindvalley.core.database.entity.*

@Database(
    entities = [NewEpisodesEntity::class, ChannelEntity::class, CategoriesEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(MediaObjectConverter::class, StringListConverter::class)
abstract class MindvalleyDatabase : RoomDatabase() {
    abstract fun mindvalleyDao(): MindvalleyDao
}