package com.thuanpx.mvvm_architecture.feature

import android.view.LayoutInflater
import com.google.android.material.navigation.NavigationBarView
import com.thuanpx.mvvm_architecture.R
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.ActivityMainBinding
import com.thuanpx.mvvm_architecture.feature.favorite.FavoriteFragment
import com.thuanpx.mvvm_architecture.feature.home.HomeFragment
import com.thuanpx.mvvm_architecture.utils.navigation.BottomNavigationManager
import com.thuanpx.mvvm_architecture.utils.navigation.createNavigationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    companion object {
        const val TAB1 = 0
        const val TAB2 = 1
    }

    private val homeFragment = HomeFragment()
    private val favoriteFragment = FavoriteFragment()

    val bottomNavigationManager: BottomNavigationManager by lazy {
        createNavigationManager {
            tabs = listOf(TAB1, TAB2)
            mainFragmentManager = supportFragmentManager
            mainContainerViewId = R.id.mainContainer
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun onBackPressed() {
        if (bottomNavigationManager.isCanPopBack()) {
            return
        }
        if (bottomNavigationManager.getCurrentTab() != TAB1) {
            viewBinding.bottomNav.selectedItemId = R.id.tab1
            bottomNavigationManager.switchTab(TAB1, homeFragment)
            return
        }
        finish()
    }

    override fun initialize() {
        bottomNavigationManager.addOrReplaceFragment(fragment = homeFragment)
        viewBinding.run {
            bottomNav.selectedItemId = R.id.tab1
            bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.tab1 -> bottomNavigationManager.switchTab(tab = TAB1, fragment = homeFragment)
                    R.id.tab2 -> bottomNavigationManager.switchTab(tab = TAB2, fragment = favoriteFragment)
                }
                return@OnItemSelectedListener true
            })
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
    }
}
