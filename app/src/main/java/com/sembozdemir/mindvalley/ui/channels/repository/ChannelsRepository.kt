package com.sembozdemir.mindvalley.ui.channels.repository

import androidx.lifecycle.LiveData
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem

interface ChannelsRepository {
    fun getItems(): LiveData<List<DisplayableItem>>
    suspend fun fetchNewEpisodes()
    suspend fun fetchChannels()
    suspend fun fetchCategories()
}