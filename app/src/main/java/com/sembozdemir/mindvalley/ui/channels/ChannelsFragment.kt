package com.sembozdemir.mindvalley.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sembozdemir.mindvalley.core.base.BaseFragment
import com.sembozdemir.mindvalley.databinding.FragmentChannelsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelsFragment : BaseFragment<FragmentChannelsBinding, ChannelsViewModel>() {

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChannelsBinding {
        return FragmentChannelsBinding.inflate(layoutInflater, container, false)
    }

    override fun onCreateViewModel() = viewModelOf<ChannelsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchData()
    }
}