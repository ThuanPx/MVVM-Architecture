package com.thuanpx.mvvm_architecture.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.thuanpx.ktext.context.launchAndRepeatWithViewLifecycle
import com.thuanpx.ktext.recyclerView.initRecyclerViewAdapter
import com.thuanpx.mvvm_architecture.base.fragment.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(HomeViewModel::class) {

    private var homeAdapter: HomeAdapter? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
        initRecyclerView()
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        with(viewModel) {
            launchAndRepeatWithViewLifecycle {
                launch {
                    pagingPokemonFlow.collectLatest {
                        homeAdapter?.submitData(it)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter()
        viewBinding.rvHome.initRecyclerViewAdapter(
            homeAdapter,
            GridLayoutManager(requireContext(), 2),
            true
        )
    }
}
