package com.sembozdemir.mindvalley.ui.channels.repository

import com.sembozdemir.mindvalley.core.network.model.CategoriesResponse
import com.sembozdemir.mindvalley.core.network.model.ChannelsResponse
import com.sembozdemir.mindvalley.ui.channels.model.NewEpisodesUIModel

interface ChannelsRepository {
    suspend fun getNewEpisodes(): NewEpisodesUIModel
    suspend fun getChannels(): ChannelsResponse
    suspend fun getCategories(): CategoriesResponse
}