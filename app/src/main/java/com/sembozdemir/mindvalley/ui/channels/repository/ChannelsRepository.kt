package com.sembozdemir.mindvalley.ui.channels.repository

import com.sembozdemir.mindvalley.ui.channels.model.CategoriesUIModel
import com.sembozdemir.mindvalley.ui.channels.model.ChannelUIModel
import com.sembozdemir.mindvalley.ui.channels.model.NewEpisodesUIModel

interface ChannelsRepository {
    suspend fun getNewEpisodes(): NewEpisodesUIModel
    suspend fun getChannels(): List<ChannelUIModel>
    suspend fun getCategories(): CategoriesUIModel
}