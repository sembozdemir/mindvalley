package com.sembozdemir.mindvalley.ui.channels.mapper

import com.sembozdemir.mindvalley.core.database.entity.CategoriesEntity
import com.sembozdemir.mindvalley.core.database.entity.ChannelEntity
import com.sembozdemir.mindvalley.core.database.entity.MediaObject
import com.sembozdemir.mindvalley.core.database.entity.NewEpisodesEntity
import com.sembozdemir.mindvalley.core.network.model.*
import org.junit.Assert.assertEquals
import org.junit.Test

private const val DUMMY_TITLE = "dummy title"
private const val DUMMY_SUBTITLE = "dummy subtitle"
private const val DUMMY_IMAGE_URL = "dummy image url"
private const val DUMMY_THUMBNAIL_URL = "dummy thumbnail url"
private const val DUMMY_MEDIA_COUNT = 77

class EntityMapperTest {

    @Test
    fun mapNewEpisodes() {
        val expectedEntity = NewEpisodesEntity(mediaObjects = dummyMediaObjects())

        val response = NewEpisodesResponse(
            data = Data(
                media = dummyMediaItems(6)
            )
        )

        val mapper = EntityMapper()

        val mappedEntity = mapper.mapNewEpisodes(response)

        assertEquals(expectedEntity, mappedEntity)
    }

    @Test
    fun mapNewEpisodes_moreThanSixElements() {
        val expectedEntity = NewEpisodesEntity(mediaObjects = dummyMediaObjects())

        val response = NewEpisodesResponse(
            data = Data(
                media = dummyMediaItems(7)
            )
        )

        val mapper = EntityMapper()

        val mappedEntity = mapper.mapNewEpisodes(response)

        assertEquals(expectedEntity, mappedEntity)
    }

    private fun dummyMediaItems(size: Int): List<MediaItem?>? {
        val mediaItems = mutableListOf<MediaItem>()
        for (i in 0 until size) {
            mediaItems.add(
                MediaItem(
                    channel = ChannelsItem(title = DUMMY_SUBTITLE),
                    type = "dummy type",
                    title = DUMMY_TITLE,
                    coverAsset = Asset(
                        url = DUMMY_IMAGE_URL
                    )
                )
            )
        }
        return mediaItems
    }

    private fun dummyMediaObjects(): List<MediaObject> {
        val mediaObjects = mutableListOf<MediaObject>()
        for (i in 0 until 6) {
            mediaObjects.add(
                MediaObject(
                    imageUrl = DUMMY_IMAGE_URL,
                    mediaTitle = DUMMY_TITLE,
                    subtitle = DUMMY_SUBTITLE
                )
            )
        }
        return mediaObjects
    }

    @Test
    fun mapChannels_isSeriesFalse() {
        val expectedEntity = dummyChannelEntities(size = 6, isSeries = false)

        val response = ChannelsResponse(
            data = Data(
                channels = dummyChannelItemsWithCourse()
            )
        )

        val mapper = EntityMapper()

        val mappedEntity = mapper.mapChannels(response)

        assertEquals(expectedEntity, mappedEntity)
    }

    @Test
    fun mapChannels_isSeriesTrue() {
        val expectedEntity = dummyChannelEntities(size = 6, isSeries = true)

        val response = ChannelsResponse(
            data = Data(
                channels = dummyChannelItemsWithSeries()
            )
        )

        val mapper = EntityMapper()

        val mappedEntity = mapper.mapChannels(response)

        assertEquals(expectedEntity, mappedEntity)
    }

    private fun dummyChannelItemsWithSeries(): List<ChannelsItem?>? {
        val channelItems = mutableListOf<ChannelsItem>()
        for (i in 0 until 6) {
            channelItems.add(
                ChannelsItem(
                    mediaCount = DUMMY_MEDIA_COUNT,
                    title = DUMMY_TITLE,
                    coverAsset = Asset(url = DUMMY_IMAGE_URL),
                    iconAsset = Asset(thumbnailUrl = DUMMY_THUMBNAIL_URL),
                    series = dummyMediaItems(6)
                )
            )
        }
        return channelItems
    }

    private fun dummyChannelItemsWithCourse(): List<ChannelsItem?>? {
        val channelItems = mutableListOf<ChannelsItem>()
        for (i in 0 until 6) {
            channelItems.add(
                ChannelsItem(
                    mediaCount = DUMMY_MEDIA_COUNT,
                    title = DUMMY_TITLE,
                    coverAsset = Asset(url = DUMMY_IMAGE_URL),
                    iconAsset = Asset(thumbnailUrl = DUMMY_THUMBNAIL_URL),
                    latestMedia = dummyMediaItems(6)
                )
            )
        }
        return channelItems
    }

    private fun dummyChannelEntities(size: Int, isSeries: Boolean): List<ChannelEntity> {
        val channelEntity = mutableListOf<ChannelEntity>()
        for (i in 0 until size) {
            channelEntity.add(
                ChannelEntity(
                    mediaObjects = dummyMediaObjects(),
                    iconImageUrl = DUMMY_THUMBNAIL_URL,
                    title = DUMMY_TITLE,
                    count = DUMMY_MEDIA_COUNT,
                    isSeries = isSeries
                )
            )
        }
        return channelEntity
    }

    @Test
    fun mapCategories() {
        val expectedEntity = CategoriesEntity(categories = dummyCategories())

        val response = CategoriesResponse(
            data = Data(
                categories = dummyCategoriesItems()
            )
        )

        val mapper = EntityMapper()

        val mappedEntity = mapper.mapCategories(response)

        assertEquals(expectedEntity, mappedEntity)
    }

    private fun dummyCategoriesItems(): List<CategoriesItem?>? {
        return listOf(
            CategoriesItem("Career"),
            CategoriesItem("Emotional"),
            CategoriesItem("Financial"),
            CategoriesItem("Life Vision")
        )
    }

    private fun dummyCategories(): List<String> {
        return listOf(
            "Career",
            "Emotional",
            "Financial",
            "Life Vision"
        )
    }
}