package com.sembozdemir.mindvalley.ui.channels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sembozdemir.mindvalley.R
import com.sembozdemir.mindvalley.core.base.BaseFragment
import com.sembozdemir.mindvalley.core.extensions.setImageUrl
import com.sembozdemir.mindvalley.core.extensions.setTextIfExists
import com.sembozdemir.mindvalley.core.glide.Transformations
import com.sembozdemir.mindvalley.core.recyclerview.HorizontalSpacingItemDecoration
import com.sembozdemir.mindvalley.core.recyclerview.VerticalDividerItemDecoration
import com.sembozdemir.mindvalley.databinding.*
import com.sembozdemir.mindvalley.ui.channels.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_category_view.view.*

@AndroidEntryPoint
class ChannelsFragment : BaseFragment<FragmentChannelsBinding, ChannelsViewModel>() {

    private val listAdapter: ListDelegationAdapter<List<DisplayableItem>> by lazy {
        ListDelegationAdapter<List<DisplayableItem>>(
            createNewEpisodesAdapterDelegate(),
            createChannelsAdapterDelegate(),
            createCategoriesAdapterDelegate()
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
        })
    }

    private fun createNewEpisodesAdapterDelegate() =
        adapterDelegateViewBinding<NewEpisodesUIModel, DisplayableItem, ItemNewEpisodesBinding>(
            { layoutInflater, parent ->
                ItemNewEpisodesBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                (0 until binding.recyclerViewNewEpisodes.itemDecorationCount).forEach {
                    binding.recyclerViewNewEpisodes.removeItemDecorationAt(it)
                }
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

    private fun createChannelsAdapterDelegate() =
        adapterDelegateViewBinding<ChannelUIModel, DisplayableItem, ItemChannelBinding>(
            { layoutInflater, parent ->
                ItemChannelBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.imageViewChannelIcon.setImageUrl(
                    item.iconImageUrl,
                    Transformations.circle()
                )
                binding.textViewChannelTitle.text = item.title
                val subtitle = if (item.isSeries) {
                    getString(R.string.channel_subtitle_series, item.count)
                } else {
                    getString(R.string.channel_subtitle_episodes, item.count)
                }
                binding.textViewChannelSubtitle.text = subtitle

                (0 until binding.recyclerViewChannel.itemDecorationCount).forEach {
                    binding.recyclerViewChannel.removeItemDecorationAt(it)
                }
                binding.recyclerViewChannel.addItemDecoration(
                    HorizontalSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.space_default))
                )

                binding.recyclerViewChannel.adapter =
                    ListDelegationAdapter<List<DisplayableItem>>(
                        if (item.isSeries) {
                            createSeriesItemAdapterDelegate()
                        } else {
                            createMediaItemAdapterDelegate()
                        }
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

    private fun createSeriesItemAdapterDelegate() =
        adapterDelegateViewBinding<MediaUIModel, DisplayableItem, ItemSeriesBinding>(
            { layoutInflater, parent ->
                ItemSeriesBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.textViewTitle.setTextIfExists(item.title)
                binding.textViewSubtitle.setTextIfExists(item.subtitle)
                binding.imageViewMedia.setImageUrl(item.imageUrl, Transformations.rounded())
            }
        }

    private fun createCategoriesAdapterDelegate() =
        adapterDelegateViewBinding<CategoriesUIModel, DisplayableItem, ItemCategoriesBinding>(
            { layoutInflater, parent ->
                ItemCategoriesBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                for (i in 0 until item.categories.size - 1 step 2) {
                    val categoryView = layoutInflater.inflate(
                        R.layout.item_category_view,
                        binding.linearLayout, false
                    )
                    categoryView.buttonCategoryStart.text = item.categories[i]
                    categoryView.buttonCategoryEnd.text = item.categories[i + 1]
                    binding.linearLayout.addView(categoryView)
                }
            }
        }
}