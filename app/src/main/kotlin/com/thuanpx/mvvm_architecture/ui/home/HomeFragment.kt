package com.thuanpx.mvvm_architecture.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.thuanpx.mvvm_architecture.common.base.BaseFragment
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
        viewBinding.btSearch.setOnClickListener {
            viewModel.searchUser(viewBinding.etKeyWord.text.toString())
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModel.run {
            users.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            })
        }
    }
}
