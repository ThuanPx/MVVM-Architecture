package com.thuanpx.view_mvvm_architecture.feature

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.viewpager2.widget.ViewPager2
import com.thuanpx.mvvm_architecture.widget.KeyboardUtils.hideSoftKeyboard
import com.thuanpx.view_mvvm_architecture.R
import com.thuanpx.view_mvvm_architecture.base.BaseActivity
import com.thuanpx.view_mvvm_architecture.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    companion object {
        const val TAB1 = 0
        const val TAB2 = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initialize() {
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewBinding.viewPager.currentItem != 0) {
                    viewBinding.viewPager.setCurrentItem(0, false)
                } else {
                    finish()
                }
            }

        })
        initBottomNav()
        initViewPager()
    }

    private fun initViewPager() {
        viewBinding.run {
            viewPager.apply {
                isUserInputEnabled = false
                adapter = MainViewPagerAdapter(this@MainActivity)
                offscreenPageLimit = 3
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        selectedBottomNav(position)
                    }
                })
            }
        }
    }

    private fun selectedBottomNav(position: Int) {
        when (position) {
            0 -> {
                viewBinding.bottomNav.post {
                    viewBinding.bottomNav.menu.findItem(R.id.tab1).isChecked = true
                }
            }
            1 -> {
                viewBinding.bottomNav.post {
                    viewBinding.bottomNav.menu.findItem(R.id.tab2).isChecked = true
                }
            }
        }
        viewBinding.viewPager.setCurrentItem(position, false)
    }

    private fun initBottomNav() {
        viewBinding.run {

            bottomNav.setOnItemSelectedListener { item ->
                hideSoftKeyboard(bottomNav)
                when (item.itemId) {
                    R.id.tab1 -> {
                        viewBinding.viewPager.setCurrentItem(0, false)
                    }

                    R.id.tab2 -> {
                        viewBinding.viewPager.setCurrentItem(1, false)
                    }

                }
                return@setOnItemSelectedListener true
            }
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
    }
}
