package com.sembozdemir.mindvalley.ui.channels.mapper

import com.sembozdemir.mindvalley.core.network.model.MediaItem
import com.sembozdemir.mindvalley.core.network.model.NewEpisodesResponse
import com.sembozdemir.mindvalley.ui.channels.model.MediaUIModel
import com.sembozdemir.mindvalley.ui.channels.model.NewEpisodesUIModel
import javax.inject.Inject

class UIModelMapper @Inject constructor() {

    fun mapNewEpisodes(response: NewEpisodesResponse): NewEpisodesUIModel {
        val mediaItems: List<MediaItem> = response.data?.media?.mapNotNull { it }.orEmpty()
        return NewEpisodesUIModel(mapNewEpisodesMedia(mediaItems))
    }

    private fun mapNewEpisodesMedia(mediaItems: List<MediaItem>): List<MediaUIModel> {
        return mediaItems.map {
            MediaUIModel(
                imageUrl = it.coverAsset?.url.orEmpty(),
                title = it.title.orEmpty(),
                subtitle = it.channel?.title.orEmpty()
            )
        }
    }

}