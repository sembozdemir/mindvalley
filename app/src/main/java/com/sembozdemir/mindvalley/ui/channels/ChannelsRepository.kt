package com.sembozdemir.mindvalley.ui.channels

import com.sembozdemir.mindvalley.core.network.model.CategoriesResponse
import com.sembozdemir.mindvalley.core.network.model.ChannelsResponse
import com.sembozdemir.mindvalley.core.network.model.NewEpisodesResponse

interface ChannelsRepository {
    suspend fun getNewEpisodes(): NewEpisodesResponse
    suspend fun getChannels(): ChannelsResponse
    suspend fun getCategories(): CategoriesResponse
}