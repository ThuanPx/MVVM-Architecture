package com.thuanpx.mvvm_architecture.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.thuanpx.mvvm_architecture.base.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(HomeViewModel::class) {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
        viewModel.fetchPokemons()
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            pokemons.observe(viewLifecycleOwner) {
            }
        }
    }
}
