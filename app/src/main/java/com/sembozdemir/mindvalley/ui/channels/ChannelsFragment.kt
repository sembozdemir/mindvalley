package com.sembozdemir.mindvalley.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.sembozdemir.mindvalley.R
import com.sembozdemir.mindvalley.core.base.BaseFragment
import com.sembozdemir.mindvalley.core.recyclerview.VerticalDividerItemDecoration
import com.sembozdemir.mindvalley.databinding.FragmentChannelsBinding
import com.sembozdemir.mindvalley.ui.channels.adapter.AdapterDelegateFactory
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChannelsFragment : BaseFragment<FragmentChannelsBinding, ChannelsViewModel>() {

    @Inject
    lateinit var adapterDelegateFactory: AdapterDelegateFactory

    private val listAdapter: ListDelegationAdapter<List<DisplayableItem>> by lazy {
        ListDelegationAdapter<List<DisplayableItem>>(
            adapterDelegateFactory.createNewEpisodesAdapterDelegate(),
            adapterDelegateFactory.createChannelsAdapterDelegate(),
            adapterDelegateFactory.createCategoriesAdapterDelegate()
        )
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChannelsBinding {
        return FragmentChannelsBinding.inflate(layoutInflater, container, false)
    }

    override fun onCreateViewModel() = viewModelOf<ChannelsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefreshLayout()
        setupRecyclerView()

        observeDisplayableItems()

        viewModel.fetchData()
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }
    }

    private fun setupRecyclerView() {
        ContextCompat.getDrawable(requireContext(), R.drawable.list_divider)?.let {
            val dividerItemDecoration = VerticalDividerItemDecoration(it)
            binding.recyclerView.addItemDecoration(dividerItemDecoration)
        }
        binding.recyclerView.adapter = listAdapter
    }

    private fun observeDisplayableItems() {

        viewModel.displayableItems.observe(viewLifecycleOwner, Observer {
            listAdapter.items = it
            listAdapter.notifyDataSetChanged() // todo: use DiffUtils
            binding.swipeRefreshLayout.isRefreshing = false
        })
    }

}