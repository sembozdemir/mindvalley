package com.sembozdemir.mindvalley.ui.channels.mapper

import com.sembozdemir.mindvalley.core.extensions.orZero
import com.sembozdemir.mindvalley.core.network.model.*
import com.sembozdemir.mindvalley.ui.channels.model.CategoriesUIModel
import com.sembozdemir.mindvalley.ui.channels.model.ChannelUIModel
import com.sembozdemir.mindvalley.ui.channels.model.MediaUIModel
import com.sembozdemir.mindvalley.ui.channels.model.NewEpisodesUIModel
import javax.inject.Inject

class UIModelMapper @Inject constructor() {

    fun mapNewEpisodes(response: NewEpisodesResponse): NewEpisodesUIModel {
        val mediaItems: List<MediaItem> = response.data?.media?.mapNotNull { it }.orEmpty()
        return NewEpisodesUIModel(mapMedia(mediaItems))
    }

    fun mapChannels(response: ChannelsResponse): List<ChannelUIModel> {
        val channelItems = response.data?.channels?.mapNotNull { it }.orEmpty()
        return channelItems.map { channel ->
            val mediaItems = if (channel.series.isNullOrEmpty()) {
                channel.latestMedia?.mapNotNull { it }.orEmpty()
            } else {
                channel.series.mapNotNull { it }
            }
            ChannelUIModel(
                mediaUIModels = mapMedia(mediaItems),
                iconImageUrl = mapIconImageUrl(channel.iconAsset),
                title = channel.title.orEmpty(),
                count = channel.mediaCount.orZero(),
                isSeries = !channel.series.isNullOrEmpty()
            )
        }
    }

    private fun mapMedia(mediaItems: List<MediaItem>): List<MediaUIModel> {
        return mediaItems.map {
            MediaUIModel(
                imageUrl = it.coverAsset?.url.orEmpty(),
                title = it.title.orEmpty(),
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

    fun mapCategories(response: CategoriesResponse): CategoriesUIModel {
        val categoryItems = response.data?.categories?.mapNotNull { it }.orEmpty()
        return CategoriesUIModel(
            categories = categoryItems.map { it.name.orEmpty() }
        )
    }

}