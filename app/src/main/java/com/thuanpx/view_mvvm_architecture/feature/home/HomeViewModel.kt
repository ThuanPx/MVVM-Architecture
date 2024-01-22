package com.thuanpx.view_mvvm_architecture.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.thuanpx.view_mvvm_architecture.base.viewmodel.BaseViewModel
import com.thuanpx.view_mvvm_architecture.data.repository.AppRepository
import com.thuanpx.view_mvvm_architecture.di.AppDispatchers
import com.thuanpx.view_mvvm_architecture.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by ThuanPx on 8/8/20.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    val pagingPokemonFlow = appRepository.fetchPokemon()
        .cachedIn(viewModelScope)
}
