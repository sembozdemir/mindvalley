package com.sembozdemir.mindvalley.ui.channels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sembozdemir.mindvalley.core.base.BaseViewModel
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem
import com.sembozdemir.mindvalley.ui.channels.repository.ChannelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ChannelsViewModel @ViewModelInject constructor(
    private val channelsRepository: ChannelsRepository
) : BaseViewModel() {

    private val _displayableItems: MutableLiveData<List<DisplayableItem>> = MutableLiveData()
    val displayableItems: LiveData<List<DisplayableItem>>
        get() = _displayableItems

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val displayableItems = mutableListOf<DisplayableItem>()
                val newEpisodes = channelsRepository.getNewEpisodes()
                displayableItems.add(newEpisodes)
                val channelUIModels = channelsRepository.getChannels()
                displayableItems.addAll(channelUIModels)
                val categoriesUIModel = channelsRepository.getCategories()
                displayableItems.add(categoriesUIModel)
                withContext(Dispatchers.Main) {
                    _displayableItems.postValue(displayableItems)
                }
            } catch (e: Exception) {
                // todo: handle exception
                Timber.e(e)
            }
        }
    }

}
