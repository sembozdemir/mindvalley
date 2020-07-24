package com.sembozdemir.mindvalley.ui.channels.mapper

import com.sembozdemir.mindvalley.core.database.entity.CategoriesEntity
import com.sembozdemir.mindvalley.core.database.entity.ChannelEntity
import com.sembozdemir.mindvalley.core.database.entity.MediaObject
import com.sembozdemir.mindvalley.core.database.entity.NewEpisodesEntity
import com.sembozdemir.mindvalley.core.extensions.orZero
import com.sembozdemir.mindvalley.core.network.model.*
import javax.inject.Inject

class EntityMapper @Inject constructor() {

    fun mapNewEpisodes(response: NewEpisodesResponse): NewEpisodesEntity {
        val mediaItems: List<MediaItem> = response.data?.media?.mapNotNull { it }.orEmpty()
        return NewEpisodesEntity(
            mediaObjects = mapMedia(mediaItems)
        )
    }

    fun mapChannels(response: ChannelsResponse): List<ChannelEntity> {
        val channelItems = response.data?.channels?.mapNotNull { it }.orEmpty()
        return channelItems.map { channel ->
            val mediaItems = if (channel.series.isNullOrEmpty()) {
                channel.latestMedia?.mapNotNull { it }.orEmpty()
            } else {
                channel.series.mapNotNull { it }
            }
            ChannelEntity(
                mediaObjects = mapMedia(mediaItems),
                iconImageUrl = mapIconImageUrl(channel.iconAsset),
                title = channel.title.orEmpty(),
                count = channel.mediaCount.orZero(),
                isSeries = !channel.series.isNullOrEmpty()
            )
        }
    }

    private fun mapMedia(mediaItems: List<MediaItem>): List<MediaObject> {
        return mediaItems.map {
            MediaObject(
                imageUrl = it.coverAsset?.url.orEmpty(),
                mediaTitle = it.title.orEmpty(),
                subtitle = it.channel?.title.orEmpty()
            )
        }
    }

    private fun mapIconImageUrl(iconAsset: Asset?): String {
        if (iconAsset == null) return ""

        if (!iconAsset.thumbnailUrl.isNullOrEmpty()) {
            return iconAsset.thumbnailUrl
        }

        if (!iconAsset.url.isNullOrEmpty()) {
            return iconAsset.url
        }

        return ""
    }

    fun mapCategories(response: CategoriesResponse): CategoriesEntity {
        val categoryItems = response.data?.categories?.mapNotNull { it }.orEmpty()
        return CategoriesEntity(
            categories = categoryItems.map { it.name.orEmpty() }
        )
    }

}