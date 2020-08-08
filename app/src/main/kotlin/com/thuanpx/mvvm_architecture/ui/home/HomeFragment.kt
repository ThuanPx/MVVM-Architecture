package com.thuanpx.mvvm_architecture.ui.home

import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.thuanpx.mvvm_architecture.common.base.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentHomeBinding
import com.thuanpx.mvvm_architecture.utils.liveData.EventObserver
import dagger.hilt.android.AndroidEntryPoint

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initialize() {
        viewBinding.btSearch.setOnClickListener {
            viewModel.searchUser(viewBinding.etKeyWord.text.toString())
        }
    }

    override fun onSubscribeObserver() {
        viewModel.run {
            users.observe(viewLifecycleOwner, EventObserver {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            })
            isLoading.observe(viewLifecycleOwner, EventObserver {
                showLoading(it)
            })
            errorMessage.observe(viewLifecycleOwner, EventObserver {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }
}
