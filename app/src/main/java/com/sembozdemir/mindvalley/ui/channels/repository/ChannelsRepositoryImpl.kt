package com.sembozdemir.mindvalley.ui.channels.repository

import com.sembozdemir.mindvalley.core.network.MindvalleyApi
import com.sembozdemir.mindvalley.core.network.model.CategoriesResponse
import com.sembozdemir.mindvalley.ui.channels.mapper.UIModelMapper
import com.sembozdemir.mindvalley.ui.channels.model.ChannelUIModel
import com.sembozdemir.mindvalley.ui.channels.model.NewEpisodesUIModel
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val mindvalleyApi: MindvalleyApi,
    private val mapper: UIModelMapper
) : ChannelsRepository {

    override suspend fun getNewEpisodes(): NewEpisodesUIModel {
        val response = mindvalleyApi.getNewEpisodes()
        return mapper.mapNewEpisodes(response)
    }

    override suspend fun getChannels(): List<ChannelUIModel> {
        val response = mindvalleyApi.getChannels()
        return mapper.mapChannels(response)
    }

    override suspend fun getCategories(): CategoriesResponse {
        return mindvalleyApi.getCategories()
    }
}