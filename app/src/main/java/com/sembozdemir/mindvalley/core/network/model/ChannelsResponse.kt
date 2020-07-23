package com.sembozdemir.mindvalley.core.network.model

import com.squareup.moshi.Json

data class ChannelsResponse(

    @Json(name = "data")
    val data: Data? = null
)