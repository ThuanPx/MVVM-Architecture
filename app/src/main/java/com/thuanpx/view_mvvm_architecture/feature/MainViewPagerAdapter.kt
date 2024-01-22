package com.thuanpx.view_mvvm_architecture.feature

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thuanpx.view_mvvm_architecture.feature.home.HomeFragment
import com.thuanpx.view_mvvm_architecture.feature.search.SearchFragment

/**
 * Created by ThuanPx on 10/02/2023.
 */
class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            else -> Fragment()
        }
    }
}
