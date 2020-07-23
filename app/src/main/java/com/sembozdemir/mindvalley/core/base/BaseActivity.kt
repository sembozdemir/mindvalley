package com.sembozdemir.mindvalley.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    abstract fun onCreateViewBinding(): B

    abstract fun onCreateViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = onCreateViewBinding()
        setContentView(binding.root)

        viewModel = onCreateViewModel()
    }

    protected inline fun <reified T : ViewModel> viewModelOf(): T {
        return ViewModelProvider(this).get(T::class.java)
    }
}