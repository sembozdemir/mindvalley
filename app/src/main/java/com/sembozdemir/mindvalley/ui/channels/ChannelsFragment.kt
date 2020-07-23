package com.sembozdemir.mindvalley.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sembozdemir.mindvalley.R
import com.sembozdemir.mindvalley.core.base.BaseFragment
import com.sembozdemir.mindvalley.core.extensions.setImageUrl
import com.sembozdemir.mindvalley.core.extensions.setTextIfExists
import com.sembozdemir.mindvalley.core.glide.Transformations
import com.sembozdemir.mindvalley.core.recyclerview.HorizontalSpacingItemDecoration
import com.sembozdemir.mindvalley.databinding.FragmentChannelsBinding
import com.sembozdemir.mindvalley.databinding.ItemMediaBinding
import com.sembozdemir.mindvalley.databinding.ItemNewEpisodesBinding
import com.sembozdemir.mindvalley.ui.channels.model.DisplayableItem
import com.sembozdemir.mindvalley.ui.channels.model.MediaUIModel
import com.sembozdemir.mindvalley.ui.channels.model.NewEpisodesUIModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelsFragment : BaseFragment<FragmentChannelsBinding, ChannelsViewModel>() {

    private val listAdapter: ListDelegationAdapter<List<DisplayableItem>> by lazy {
        ListDelegationAdapter<List<DisplayableItem>>(
            createNewEpisodesAdapterDelegate()
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

        setupRecyclerView()

        observeDisplayableItems()

        viewModel.fetchData()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = listAdapter
    }

    private fun observeDisplayableItems() {

        viewModel.displayableItems.observe(viewLifecycleOwner, Observer {
            listAdapter.items = it
            listAdapter.notifyDataSetChanged()
        })
    }

    private fun createNewEpisodesAdapterDelegate() =
        adapterDelegateViewBinding<NewEpisodesUIModel, DisplayableItem, ItemNewEpisodesBinding>(
            { layoutInflater, parent ->
                ItemNewEpisodesBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.recyclerViewNewEpisodes.addItemDecoration(
                    HorizontalSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.space_default))
                )
                binding.recyclerViewNewEpisodes.adapter =
                    ListDelegationAdapter<List<DisplayableItem>>(
                        createMediaItemAdapterDelegate()
                    ).apply {
                        items = item.mediaUIModels
                    }
            }
        }

    private fun createMediaItemAdapterDelegate() =
        adapterDelegateViewBinding<MediaUIModel, DisplayableItem, ItemMediaBinding>(
            { layoutInflater, parent ->
                ItemMediaBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.textViewTitle.setTextIfExists(item.title)
                binding.textViewSubtitle.setTextIfExists(item.subtitle)
                binding.imageViewMedia.setImageUrl(item.imageUrl, Transformations.rounded())
            }
        }
}