package com.sembozdemir.mindvalley.core.network.model

import com.squareup.moshi.Json

data class Data(

    @Json(name = "media")
    val media: List<MediaItem?>? = null,

    @Json(name = "channels")
    val channels: List<ChannelsItem?>? = null,

    @Json(name = "categories")
    val categories: List<CategoriesItem?>? = null
)