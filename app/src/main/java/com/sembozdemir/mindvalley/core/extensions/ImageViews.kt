package com.sembozdemir.mindvalley.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageUrl(url: String, requestOptions: RequestOptions? = null) {
    val request = Glide.with(context).load(url)
    requestOptions?.let {
        request.apply(requestOptions)
    }
    request.into(this)
}