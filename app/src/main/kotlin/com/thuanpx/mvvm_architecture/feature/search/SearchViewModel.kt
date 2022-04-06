package com.thuanpx.mvvm_architecture.feature.search

import androidx.lifecycle.viewModelScope
import com.thuanpx.ktext.flow.loading
import com.thuanpx.mvvm_architecture.base.viewmodel.BaseViewModel
import com.thuanpx.mvvm_architecture.data.repository.AppRepository
import com.thuanpx.mvvm_architecture.di.IoDispatcher
import com.thuanpx.mvvm_architecture.model.entity.PokemonInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ThuanPx on 4/3/22.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    val query = MutableStateFlow("")
    val pokemonInfo = MutableStateFlow(PokemonInfo())

    init {
        viewModelScope.launch {
            query
                .debounce(2000)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .flatMapLatest { query ->
                    appRepository.fetchPokemonInfo(query)
                        .loading(isLoading)
                        .catch { exception.postValue(it) }
                }
                .flowOn(ioDispatcher)
                .collect {
                  pokemonInfo.emit(it)
                }
        }
    }
}
