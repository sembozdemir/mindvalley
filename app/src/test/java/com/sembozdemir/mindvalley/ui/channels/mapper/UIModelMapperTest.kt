package com.sembozdemir.mindvalley.ui.channels.mapper

import com.sembozdemir.mindvalley.core.database.entity.CategoriesEntity
import com.sembozdemir.mindvalley.core.database.entity.ChannelEntity
import com.sembozdemir.mindvalley.core.database.entity.MediaObject
import com.sembozdemir.mindvalley.core.database.entity.NewEpisodesEntity
import com.sembozdemir.mindvalley.ui.channels.model.CategoriesUIModel
import com.sembozdemir.mindvalley.ui.channels.model.ChannelUIModel
import com.sembozdemir.mindvalley.ui.channels.model.MediaUIModel
import com.sembozdemir.mindvalley.ui.channels.model.NewEpisodesUIModel
import org.junit.Assert.assertEquals
import org.junit.Test

private const val DUMMY_TITLE = "dummy title"
private const val DUMMY_SUBTITLE = "dummy subtitle"
private const val DUMMY_IMAGE_URL = "dummy image url"
private const val DUMMY_THUMBNAIL_URL = "dummy thumbnail url"
private const val DUMMY_MEDIA_COUNT = 77

class UIModelMapperTest {

    @Test
    fun mapNewEpisodes() {
        val expectedUIModel = NewEpisodesUIModel(dummyMediaUIModels())

        val entity = NewEpisodesEntity(mediaObjects = dummyMediaObjects())

        val mapper = UIModelMapper()

        val mappedUIModel = mapper.mapNewEpisodes(entity)

        assertEquals(expectedUIModel, mappedUIModel)
    }

    private fun dummyMediaUIModels(): List<MediaUIModel> {
        val mediaUIModels = mutableListOf<MediaUIModel>()
        for (i in 0 until 6) {
            mediaUIModels.add(
                MediaUIModel(
                    imageUrl = DUMMY_IMAGE_URL,
                    title = DUMMY_TITLE,
                    subtitle = DUMMY_SUBTITLE
                )
            )
        }
        return mediaUIModels
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
    fun mapChannels() {
        val expectedUIModels = listOf(
            ChannelUIModel(
                mediaUIModels = dummyMediaUIModels(),
                iconImageUrl = DUMMY_THUMBNAIL_URL,
                title = DUMMY_TITLE,
                count = DUMMY_MEDIA_COUNT,
                isSeries = false
            )
        )

        val entities = listOf(
            ChannelEntity(
                mediaObjects = dummyMediaObjects(),
                title = DUMMY_TITLE,
                count = DUMMY_MEDIA_COUNT,
                iconImageUrl = DUMMY_THUMBNAIL_URL,
                isSeries = false
            )
        )

        val mapper = UIModelMapper()

        val mappedUIModels = mapper.mapChannels(entities)

        assertEquals(expectedUIModels, mappedUIModels)
    }

    @Test
    fun mapCategories() {
        val expectedUIModel = CategoriesUIModel(categories = dummyCategories())

        val entity = CategoriesEntity(categories = dummyCategories())

        val mapper = UIModelMapper()

        val mappedUIModel = mapper.mapCategories(entity)

        assertEquals(expectedUIModel, mappedUIModel)
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