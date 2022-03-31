package com.thuanpx.mvvm_architecture.feature.home

import com.thuanpx.mvvm_architecture.base.BaseViewModel
import com.thuanpx.mvvm_architecture.data.repository.AppRepository
import com.thuanpx.mvvm_architecture.di.IoDispatcher
import com.thuanpx.mvvm_architecture.utils.extension.loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val page = MutableStateFlow(0)
    val pokemonList = page.flatMapLatest { page ->
        appRepository.fetchPokemonList(page)
            .loading(isLoading)
            .catch {
                exception.postValue(it)
            }
    }.flowOn(ioDispatcher)

}

