package com.sembozdemir.mindvalley.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMindvalleyDatabase(@ApplicationContext context: Context): MindvalleyDatabase {
        return Room.databaseBuilder(context, MindvalleyDatabase::class.java, "mindvalley_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMindvalleyDao(database: MindvalleyDatabase): MindvalleyDao = database.mindvalleyDao()
}