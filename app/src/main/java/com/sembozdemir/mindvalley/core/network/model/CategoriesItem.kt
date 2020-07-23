package com.sembozdemir.mindvalley.core.network.model

import com.squareup.moshi.Json

data class CategoriesItem(

    @Json(name = "name")
    val name: String? = null
)