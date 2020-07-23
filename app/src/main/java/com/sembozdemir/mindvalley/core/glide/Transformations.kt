package com.sembozdemir.mindvalley.core.glide

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object Transformations {
    fun rounded() = RequestOptions().transform(CenterCrop(), RoundedCorners(24))
    fun circle() = RequestOptions().transform(CenterCrop(), CircleCrop())
}