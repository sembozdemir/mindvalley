package com.sembozdemir.mindvalley.ui.channels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.sembozdemir.mindvalley.core.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ChannelsViewModel @ViewModelInject constructor(
    private val channelsRepository: ChannelsRepository
) : BaseViewModel() {

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = channelsRepository.getNewEpisodes()
            Timber.d(response.data?.media?.firstOrNull()?.title.orEmpty())
            // todo: handle responses
        }
    }

}
