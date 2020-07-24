package com.sembozdemir.mindvalley.ui.channels

import com.sembozdemir.mindvalley.ui.channels.adapter.AdapterDelegateFactory
import com.sembozdemir.mindvalley.ui.channels.adapter.AdapterDelegateFactoryImpl
import com.sembozdemir.mindvalley.ui.channels.repository.ChannelsRepository
import com.sembozdemir.mindvalley.ui.channels.repository.ChannelsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ChannelsModule {

    @Binds
    abstract fun bindChannelsRepository(channelsRepositoryImpl: ChannelsRepositoryImpl): ChannelsRepository

    @Binds
    abstract fun bindAdapterDelegate(adapterDelegateFactoryImpl: AdapterDelegateFactoryImpl): AdapterDelegateFactory
}