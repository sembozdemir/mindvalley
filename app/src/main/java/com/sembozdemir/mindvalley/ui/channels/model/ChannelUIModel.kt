package com.sembozdemir.mindvalley.ui.channels.model

data class ChannelUIModel(
    val mediaUIModels: List<MediaUIModel>,
    val iconImageUrl: String,
    val title: String,
    val subtitle: String,
    val isSeries: Boolean
) : DisplayableItem