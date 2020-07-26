package com.sembozdemir.mindvalley.ui.channels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sembozdemir.mindvalley.core.base.BaseViewModel
import com.sembozdemir.mindvalley.core.livedata.SingleLiveEvent
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem
import com.sembozdemir.mindvalley.ui.channels.repository.ChannelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ChannelsViewModel @ViewModelInject constructor(
    private val channelsRepository: ChannelsRepository
) : BaseViewModel() {

    val displayableItems: LiveData<List<DisplayableItem>> = channelsRepository.getItems()

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _errorEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val errorEvent: LiveData<Unit>
        get() = _errorEvent

    fun fetchData(showLoading: Boolean = true) {
        if (showLoading) {
            _showLoading.postValue(true)
        }

        viewModelScope.launch {
            try {
                listOf(
                    viewModelScope.async(Dispatchers.IO) { channelsRepository.fetchNewEpisodes() },
                    viewModelScope.async(Dispatchers.IO) { channelsRepository.fetchChannels() },
                    viewModelScope.async(Dispatchers.IO) { channelsRepository.fetchCategories() }
                ).awaitAll()

                _showLoading.postValue(false)
            } catch (e: Exception) {
                _showLoading.postValue(false)
                _errorEvent.call()
            }

        }
    }

}
