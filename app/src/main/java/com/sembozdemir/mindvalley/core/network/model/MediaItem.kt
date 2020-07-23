package com.sembozdemir.mindvalley.core.network.model

import com.squareup.moshi.Json

data class MediaItem(

    @Json(name = "channel")
    val channel: ChannelsItem? = null,

    @Json(name = "type")
    val type: String? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "coverAsset")
    val coverAsset: Asset? = null
)