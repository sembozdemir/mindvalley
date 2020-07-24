package com.sembozdemir.mindvalley.ui.channels.adapter

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sembozdemir.mindvalley.R
import com.sembozdemir.mindvalley.core.extensions.setImageUrl
import com.sembozdemir.mindvalley.core.extensions.setTextIfExists
import com.sembozdemir.mindvalley.core.glide.Transformations
import com.sembozdemir.mindvalley.core.recyclerview.HorizontalSpacingItemDecoration
import com.sembozdemir.mindvalley.databinding.*
import com.sembozdemir.mindvalley.ui.channels.model.*
import kotlinx.android.synthetic.main.item_category_view.view.*
import javax.inject.Inject

class AdapterDelegateFactoryImpl @Inject constructor() : AdapterDelegateFactory {

    override fun createNewEpisodesAdapterDelegate(): AdapterDelegate<List<DisplayableItem>> =
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
                    HorizontalSpacingItemDecoration(context.resources.getDimensionPixelSize(R.dimen.space_default))
                )
                binding.recyclerViewNewEpisodes.adapter =
                    ListDelegationAdapter<List<DisplayableItem>>(
                        createMediaItemAdapterDelegate()
                    ).apply {
                        items = item.mediaUIModels
                    }
            }
        }

    override fun createChannelsAdapterDelegate(): AdapterDelegate<List<DisplayableItem>> =
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
                    HorizontalSpacingItemDecoration(context.resources.getDimensionPixelSize(R.dimen.space_default))
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

    override fun createCategoriesAdapterDelegate(): AdapterDelegate<List<DisplayableItem>> =
        adapterDelegateViewBinding<CategoriesUIModel, DisplayableItem, ItemCategoriesBinding>(
            { layoutInflater, parent ->
                ItemCategoriesBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                binding.linearLayout.removeAllViews()
                for (i in 0 until item.categories.size - 1 step 2) {
                    val categoryView = LayoutInflater.from(context).inflate(
                        R.layout.item_category_view,
                        binding.linearLayout, false
                    )
                    categoryView.buttonCategoryStart.text = item.categories[i]
                    categoryView.buttonCategoryEnd.text = item.categories[i + 1]
                    binding.linearLayout.addView(categoryView)
                }
            }
        }

    override fun createMediaItemAdapterDelegate(): AdapterDelegate<List<DisplayableItem>> =
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

    override fun createSeriesItemAdapterDelegate(): AdapterDelegate<List<DisplayableItem>> =
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
}