package com.sembozdemir.mindvalley.core.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sembozdemir.mindvalley.core.database.entity.CategoriesEntity
import com.sembozdemir.mindvalley.core.database.entity.ChannelEntity
import com.sembozdemir.mindvalley.core.database.entity.NewEpisodesEntity

@Dao
interface MindvalleyDao {

    @Query("SELECT * FROM newEpisodes WHERE id LIKE :id LIMIT 1")
    fun getNewEpisodesLiveData(id: String = NewEpisodesEntity.CACHED): LiveData<NewEpisodesEntity>

    @Query("SELECT * FROM category WHERE id LIKE :id LIMIT 1")
    fun getCategoriesLiveData(id: String = CategoriesEntity.CACHED): LiveData<CategoriesEntity>

    @Query("SELECT * FROM channel")
    fun getChannelsLiveData(): LiveData<List<ChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheNewEpisodes(newEpisodesEntity: NewEpisodesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheCategories(categoriesEntity: CategoriesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheChannels(channels: List<ChannelEntity>)
}