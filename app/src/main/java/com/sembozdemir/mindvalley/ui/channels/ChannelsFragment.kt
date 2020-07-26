package com.sembozdemir.mindvalley.ui.channels

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.sembozdemir.mindvalley.R
import com.sembozdemir.mindvalley.core.base.BaseFragment
import com.sembozdemir.mindvalley.core.extensions.action
import com.sembozdemir.mindvalley.core.extensions.snack
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
        observeShowLoadingEvent()
        observeErrorEvent()

        viewModel.fetchData()
    }

    private fun observeErrorEvent() {
        viewModel.errorEvent.observe(viewLifecycleOwner, Observer {
            binding.coordinatorLayout.snack(R.string.general_error, Snackbar.LENGTH_INDEFINITE) {
                setTextColor(Color.WHITE)
                setBackgroundTint(Color.RED)
                action(R.string.retry, Color.WHITE) {
                    viewModel.fetchData()
                }
            }
        })
    }

    private fun observeShowLoadingEvent() {
        viewModel.showLoading.observe(viewLifecycleOwner, Observer { show ->
            if (show) {
                binding.shimmerFrameLayout.isVisible = true
                binding.recyclerView.isVisible = false
            } else {
                binding.shimmerFrameLayout.isVisible = false
                binding.recyclerView.isVisible = true
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchData(showLoading = false)
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
            listAdapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
        })
    }

}