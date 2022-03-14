package com.thuanpx.mvvm_architecture.feature.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import com.thuanpx.mvvm_architecture.base.BaseFragment
import com.thuanpx.mvvm_architecture.base.EmptyViewModel
import com.thuanpx.mvvm_architecture.databinding.FragmentFavoriteBinding
import com.thuanpx.mvvm_architecture.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<EmptyViewModel, FragmentFavoriteBinding>(EmptyViewModel::class) {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
    }
}
