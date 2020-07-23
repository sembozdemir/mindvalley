package com.sembozdemir.mindvalley.ui.main

import com.sembozdemir.mindvalley.core.base.BaseActivity
import com.sembozdemir.mindvalley.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreateViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreateViewModel() = viewModelOf<MainViewModel>()

}