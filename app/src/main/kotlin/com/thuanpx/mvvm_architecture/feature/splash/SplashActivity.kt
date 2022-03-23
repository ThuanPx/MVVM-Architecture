package com.thuanpx.mvvm_architecture.feature.splash

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.thuanpx.ktext.context.rootTo
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.ActivitySplashBinding
import com.thuanpx.mvvm_architecture.feature.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 16/09/2021.
 */
@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>(SplashViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }

    override fun initialize() {
        lifecycleScope.launch {
            delay(2_000)
            rootTo(MainActivity::class)
        }
    }
}
