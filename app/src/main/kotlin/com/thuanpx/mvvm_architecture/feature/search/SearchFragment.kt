package com.thuanpx.mvvm_architecture.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doBeforeTextChanged
import com.thuanpx.ktext.context.launchAndRepeatWithViewLifecycle
import com.thuanpx.mvvm_architecture.base.fragment.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by ThuanPx on 8/8/20.
 */

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(SearchViewModel::class) {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
        viewBinding.etSearch.doBeforeTextChanged { text, start, count, after ->
            viewModel.query.value = text.toString()
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.pokemonInfo.collect {
                    Timber.i("$it")
                }
            }
        }
    }
}
