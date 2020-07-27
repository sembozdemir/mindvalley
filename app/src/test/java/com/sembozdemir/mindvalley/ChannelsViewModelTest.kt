package com.sembozdemir.mindvalley

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sembozdemir.mindvalley.core.coroutines.DispatcherProvider
import com.sembozdemir.mindvalley.ui.channels.ChannelsViewModel
import com.sembozdemir.mindvalley.ui.channels.repository.ChannelsRepository
import com.sembozdemir.mindvalley.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class ChannelsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    val testDispatcher: TestCoroutineDispatcher
        get() = coroutineTestRule.testDispatcher

    @ExperimentalCoroutinesApi
    val dispatcherProvider: DispatcherProvider
        get() = coroutineTestRule.testDispatcherProvider

    @ExperimentalCoroutinesApi
    @Test
    fun `errorEvent should be called in case of exception`() = testDispatcher.runBlockingTest {
        // Arrange
        val repository = mock<ChannelsRepository>()
        val viewModel = ChannelsViewModel(dispatcherProvider, repository)
        val observer = mock<Observer<Unit>>()

        whenever(repository.fetchNewEpisodes()).thenThrow(RuntimeException())

        // Act
        viewModel.errorEvent.observeForever(observer)
        viewModel.fetchData()

        // Assert
        verify(observer).onChanged(null)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `showLoading should be true if it is true as fetch parameter`() =
        testDispatcher.runBlockingTest {
            // Arrange
            val repository = mock<ChannelsRepository>()
            val viewModel = ChannelsViewModel(dispatcherProvider, repository)
            val observer = mock<Observer<Boolean>>()

            whenever(repository.fetchNewEpisodes()).thenReturn(Unit)
            whenever(repository.fetchChannels()).thenReturn(Unit)
            whenever(repository.fetchCategories()).thenReturn(Unit)

            // Act
            viewModel.showLoading.observeForever(observer)
            viewModel.fetchData(showLoading = true)

            // Assert
            verify(observer).onChanged(true)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `showLoading should be false if it is false as fetch parameter`() =
        testDispatcher.runBlockingTest {
            // Arrange
            val repository = mock<ChannelsRepository>()
            val viewModel = ChannelsViewModel(dispatcherProvider, repository)
            val observer = mock<Observer<Boolean>>()

            whenever(repository.fetchNewEpisodes()).thenReturn(Unit)
            whenever(repository.fetchChannels()).thenReturn(Unit)
            whenever(repository.fetchCategories()).thenReturn(Unit)

            // Act
            viewModel.showLoading.observeForever(observer)
            viewModel.fetchData(showLoading = false)

            // Assert
            verify(observer).onChanged(false)
        }

}