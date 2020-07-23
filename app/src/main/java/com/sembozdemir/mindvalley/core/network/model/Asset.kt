package com.sembozdemir.mindvalley.core.network.model

import com.squareup.moshi.Json

data class Asset(

    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String? = null,

    @Json(name = "url")
    val url: String? = null
)