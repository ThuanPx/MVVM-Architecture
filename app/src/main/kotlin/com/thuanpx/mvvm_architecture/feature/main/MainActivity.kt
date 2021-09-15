package com.thuanpx.mvvm_architecture.feature.main

import android.view.LayoutInflater
import com.thuanpx.ktext.context.replaceFragment
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.ActivityMainBinding
import com.thuanpx.mvvm_architecture.feature.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initialize() {
        replaceFragment(
            viewBinding.fragmentContainerView.id,
            HomeFragment(),
            addToBackStack = false
        )
    }

    override fun onSubscribeObserver() {
    }
}
