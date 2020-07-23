package com.sembozdemir.mindvalley.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding, VM : BaseViewModel> : Fragment() {

    private var _binding: B? = null

    // This property is only valid between onCreateView and onDestroyView.
    protected val binding
        get() = _binding!!

    protected lateinit var viewModel: VM

    abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun onCreateViewModel(): VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = onCreateViewBinding(inflater, container)
        viewModel = onCreateViewModel()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected inline fun <reified T : ViewModel> viewModelOf(): T {
        return ViewModelProvider(this).get(T::class.java)
    }
}