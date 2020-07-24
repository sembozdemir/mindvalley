package com.sembozdemir.mindvalley.ui.channels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sembozdemir.mindvalley.core.base.BaseViewModel
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem
import com.sembozdemir.mindvalley.ui.channels.repository.ChannelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ChannelsViewModel @ViewModelInject constructor(
    private val channelsRepository: ChannelsRepository
) : BaseViewModel() {

    val displayableItems: LiveData<List<DisplayableItem>> = channelsRepository.getItems()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // todo: send those requests parallel with Kotlin Flow or RxJava
                channelsRepository.fetchNewEpisodes()
                channelsRepository.fetchChannels()
                channelsRepository.fetchCategories()
            } catch (e: Exception) {
                // todo: handle exception
                Timber.e(e)
            }
        }
    }

}
