package com.sembozdemir.mindvalley.core.database.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaObject(

    @Json(name = "mediaTitle")
    var mediaTitle: String = "",

    @Json(name = "subtitle")
    var subtitle: String = "",

    @Json(name = "imageUrl")
    var imageUrl: String = ""
)