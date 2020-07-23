package com.sembozdemir.mindvalley.ui.channels

import com.sembozdemir.mindvalley.core.network.MindvalleyApi
import com.sembozdemir.mindvalley.core.network.model.CategoriesResponse
import com.sembozdemir.mindvalley.core.network.model.ChannelsResponse
import com.sembozdemir.mindvalley.core.network.model.NewEpisodesResponse
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val mindvalleyApi: MindvalleyApi
) : ChannelsRepository {

    override suspend fun getNewEpisodes(): NewEpisodesResponse {
        return mindvalleyApi.getNewEpisodes()
    }

    override suspend fun getChannels(): ChannelsResponse {
        return mindvalleyApi.getChannels()
    }

    override suspend fun getCategories(): CategoriesResponse {
        return mindvalleyApi.getCategories()
    }
}