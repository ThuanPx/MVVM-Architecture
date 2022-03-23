package com.thuanpx.mvvm_architecture.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import com.thuanpx.mvvm_architecture.base.BaseFragment
import com.thuanpx.mvvm_architecture.base.EmptyViewModel
import com.thuanpx.mvvm_architecture.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 16/09/2021.
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<EmptyViewModel, FragmentMainBinding>(EmptyViewModel::class) {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun initialize() {

    }
}