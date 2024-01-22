package com.thuanpx.view_mvvm_architecture.feature.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.thuanpx.view_mvvm_architecture.base.BaseActivity
import com.thuanpx.view_mvvm_architecture.base.viewmodel.EmptyViewModel
import com.thuanpx.view_mvvm_architecture.databinding.ActivitySplashBinding
import com.thuanpx.view_mvvm_architecture.feature.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by ThuanPx on 16/09/2021.
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<EmptyViewModel, ActivitySplashBinding>(EmptyViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }

    override fun initialize() {
        lifecycleScope.launch {
            delay(1_000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
