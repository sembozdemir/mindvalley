package com.sembozdemir.mindvalley.ui.channels.model

data class ChannelUIModel(
    val mediaUIModels: List<MediaUIModel>,
    val iconImageUrl: String,
    val title: String,
    val count: Int,
    val isSeries: Boolean
) : DisplayableItem