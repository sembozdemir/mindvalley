package com.sembozdemir.mindvalley.ui.channels.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.sembozdemir.mindvalley.core.database.MindvalleyDao
import com.sembozdemir.mindvalley.core.database.entity.CategoriesEntity
import com.sembozdemir.mindvalley.core.database.entity.ChannelEntity
import com.sembozdemir.mindvalley.core.database.entity.NewEpisodesEntity
import com.sembozdemir.mindvalley.core.network.MindvalleyApi
import com.sembozdemir.mindvalley.ui.channels.mapper.EntityMapper
import com.sembozdemir.mindvalley.ui.channels.mapper.UIModelMapper
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val mindvalleyApi: MindvalleyApi,
    private val uiModelMapper: UIModelMapper,
    private val entityMapper: EntityMapper,
    private val mindvalleyDao: MindvalleyDao
) : ChannelsRepository {

    private val displayableItems: MediatorLiveData<List<DisplayableItem>> = MediatorLiveData()

    private val newEpisodesEntity: LiveData<NewEpisodesEntity> =
        mindvalleyDao.getNewEpisodesLiveData()
    private val channelsEntities: LiveData<List<ChannelEntity>> =
        mindvalleyDao.getChannelsLiveData()
    private val categoriesEntity: LiveData<CategoriesEntity> =
        mindvalleyDao.getCategoriesLiveData()

    init {
        displayableItems.addSource(newEpisodesEntity) { entity ->
            val items = mutableListOf<DisplayableItem>()

            entity?.let {
                items.add(uiModelMapper.mapNewEpisodes(it))
            }

            addChannels(items)
            addCategories(items)

            displayableItems.postValue(items)
        }

        displayableItems.addSource(channelsEntities) { channelList ->
            val items = mutableListOf<DisplayableItem>()

            addNewEpisodes(items)

            items.addAll(uiModelMapper.mapChannels(channelList.orEmpty()))

            addCategories(items)

            displayableItems.postValue(items)
        }

        displayableItems.addSource(categoriesEntity) { entity ->
            val items = mutableListOf<DisplayableItem>()

            addNewEpisodes(items)
            addChannels(items)

            entity?.let {
                items.add(uiModelMapper.mapCategories(it))
            }

            displayableItems.postValue(items)
        }
    }

    private fun addNewEpisodes(items: MutableList<DisplayableItem>) {
        newEpisodesEntity.value?.let {
            items.add(uiModelMapper.mapNewEpisodes(it))
        }
    }

    private fun addCategories(items: MutableList<DisplayableItem>) {
        categoriesEntity.value?.let {
            items.add(uiModelMapper.mapCategories(it))
        }
    }

    private fun addChannels(items: MutableList<DisplayableItem>) {
        channelsEntities.value?.let {
            items.addAll(uiModelMapper.mapChannels(it))
        }
    }

    override fun getItems(): LiveData<List<DisplayableItem>> {
        return displayableItems
    }

    override suspend fun fetchNewEpisodes() {
        val response = mindvalleyApi.getNewEpisodes()
        val newEpisodes = entityMapper.mapNewEpisodes(response)
        mindvalleyDao.cacheNewEpisodes(newEpisodes)
    }

    override suspend fun fetchChannels() {
        val response = mindvalleyApi.getChannels()
        val channels = entityMapper.mapChannels(response)
        mindvalleyDao.cacheChannels(channels)
    }

    override suspend fun fetchCategories() {
        val response = mindvalleyApi.getCategories()
        val categories = entityMapper.mapCategories(response)
        mindvalleyDao.cacheCategories(categories)
    }
}