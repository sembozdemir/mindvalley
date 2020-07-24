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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

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

    fun fetchData() {
        _showLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // todo: send those requests parallel with Kotlin Flow or RxJava
                channelsRepository.fetchNewEpisodes()
                channelsRepository.fetchChannels()
                channelsRepository.fetchCategories()
                withContext(Dispatchers.Main) {
                    _showLoading.postValue(false)
                }
            } catch (e: Exception) {
                Timber.e(e)
                withContext(Dispatchers.Main) {
                    _showLoading.postValue(false)
                    _errorEvent.call()
                }
            }
        }
    }

}
