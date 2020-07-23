package com.sembozdemir.mindvalley.core.network.model

import com.squareup.moshi.Json

data class ChannelsItem(

    @Json(name = "series")
    val series: List<MediaItem?>? = null,

    @Json(name = "mediaCount")
    val mediaCount: Int? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "coverAsset")
    val coverAsset: Asset? = null,

    @Json(name = "slug")
    val slug: String? = null,

    @Json(name = "latestMedia")
    val latestMedia: List<MediaItem?>? = null,

    @Json(name = "iconAsset")
    val iconAsset: Asset? = null,

    @Json(name = "id")
    val id: String? = null
)