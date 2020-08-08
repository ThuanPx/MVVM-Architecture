package com.thuanpx.mvvm_architecture.ui.main

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.thuanpx.ktext.context.replaceFragmentToActivity
import com.thuanpx.mvvm_architecture.common.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.ActivityMainBinding
import com.thuanpx.mvvm_architecture.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    private val viewModel by viewModels<MainViewModel>()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initialize() {
        replaceFragmentToActivity(viewBinding.fragmentContainerView.id, HomeFragment())
    }

    override fun onSubscribeObserver() {
    }
}
