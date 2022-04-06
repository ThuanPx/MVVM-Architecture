package com.thuanpx.mvvm_architecture.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.thuanpx.mvvm_architecture.base.viewmodel.BaseViewModel
import com.thuanpx.mvvm_architecture.data.repository.AppRepository
import com.thuanpx.mvvm_architecture.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by ThuanPx on 8/8/20.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    val pagingPokemonFlow = appRepository.fetchPokemon(isLoading)
        .cachedIn(viewModelScope)


}

