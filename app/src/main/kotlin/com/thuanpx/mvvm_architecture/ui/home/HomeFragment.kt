package com.thuanpx.mvvm_architecture.ui.home

import android.view.LayoutInflater
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

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
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
