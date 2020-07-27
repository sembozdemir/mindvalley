package com.sembozdemir.mindvalley.ui.channels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sembozdemir.mindvalley.core.base.BaseViewModel
import com.sembozdemir.mindvalley.core.coroutines.DispatcherProvider
import com.sembozdemir.mindvalley.core.livedata.SingleLiveEvent
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem
import com.sembozdemir.mindvalley.ui.channels.repository.ChannelsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ChannelsViewModel @ViewModelInject constructor(
    private val dispatchers: DispatcherProvider,
    private val channelsRepository: ChannelsRepository
) : BaseViewModel() {

    val displayableItems: LiveData<List<DisplayableItem>> = channelsRepository.getItems()

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _errorEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val errorEvent: LiveData<Unit>
        get() = _errorEvent

    fun fetchData(showLoading: Boolean = true) {
        viewModelScope.launch(dispatchers.main()) {
            if (showLoading) {
                _showLoading.postValue(true)
            }

            try {
                listOf(
                    viewModelScope.async(dispatchers.io()) { channelsRepository.fetchNewEpisodes() },
                    viewModelScope.async(dispatchers.io()) { channelsRepository.fetchChannels() },
                    viewModelScope.async(dispatchers.io()) { channelsRepository.fetchCategories() }
                ).awaitAll()

                _showLoading.postValue(false)
            } catch (e: Exception) {
                _showLoading.postValue(false)
                _errorEvent.call()
            }

        }
    }

}
