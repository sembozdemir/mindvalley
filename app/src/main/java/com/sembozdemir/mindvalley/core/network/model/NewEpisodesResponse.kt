package com.sembozdemir.mindvalley.core.network.model

import com.squareup.moshi.Json

data class NewEpisodesResponse(

    @Json(name = "data")
    val data: Data? = null
)