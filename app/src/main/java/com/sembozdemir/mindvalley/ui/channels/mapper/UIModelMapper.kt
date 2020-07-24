package com.sembozdemir.mindvalley.ui.channels.mapper

import com.sembozdemir.mindvalley.core.database.entity.CategoriesEntity
import com.sembozdemir.mindvalley.core.database.entity.ChannelEntity
import com.sembozdemir.mindvalley.core.database.entity.MediaObject
import com.sembozdemir.mindvalley.core.database.entity.NewEpisodesEntity
import com.sembozdemir.mindvalley.ui.channels.model.CategoriesUIModel
import com.sembozdemir.mindvalley.ui.channels.model.ChannelUIModel
import com.sembozdemir.mindvalley.ui.channels.model.MediaUIModel
import com.sembozdemir.mindvalley.ui.channels.model.NewEpisodesUIModel
import javax.inject.Inject

class UIModelMapper @Inject constructor() {

    fun mapNewEpisodes(entity: NewEpisodesEntity): NewEpisodesUIModel {
        return NewEpisodesUIModel(mapMedia(entity.mediaObjects))
    }

    fun mapChannels(channelEntities: List<ChannelEntity>): List<ChannelUIModel> {
        return channelEntities.map { channel ->
            ChannelUIModel(
                mediaUIModels = mapMedia(channel.mediaObjects),
                iconImageUrl = channel.iconImageUrl,
                title = channel.title,
                count = channel.count,
                isSeries = channel.isSeries
            )
        }
    }

    private fun mapMedia(mediaObjects: List<MediaObject>): List<MediaUIModel> {
        return mediaObjects.map {
            MediaUIModel(
                imageUrl = it.imageUrl,
                title = it.mediaTitle,
                subtitle = it.subtitle
            )
        }
    }

    fun mapCategories(categoriesEntity: CategoriesEntity): CategoriesUIModel {
        return CategoriesUIModel(
            categories = categoriesEntity.categories
        )
    }

}