package com.sembozdemir.mindvalley.ui.channels.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem

interface AdapterDelegateFactory {
    fun createNewEpisodesAdapterDelegate(): AdapterDelegate<List<DisplayableItem>>
    fun createChannelsAdapterDelegate(): AdapterDelegate<List<DisplayableItem>>
    fun createCategoriesAdapterDelegate(): AdapterDelegate<List<DisplayableItem>>
    fun createMediaItemAdapterDelegate(): AdapterDelegate<List<DisplayableItem>>
    fun createSeriesItemAdapterDelegate(): AdapterDelegate<List<DisplayableItem>>
}