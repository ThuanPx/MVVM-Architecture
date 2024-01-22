package com.thuanpx.view_mvvm_architecture.feature.search

import androidx.lifecycle.viewModelScope
import com.thuanpx.view_mvvm_architecture.base.viewmodel.BaseViewModel
import com.thuanpx.view_mvvm_architecture.data.repository.AppRepository
import com.thuanpx.view_mvvm_architecture.di.AppDispatchers
import com.thuanpx.view_mvvm_architecture.di.Dispatcher
import com.thuanpx.view_mvvm_architecture.model.entity.PokemonInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ThuanPx on 4/3/22.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
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
                        .catch { _error.emit(it) }
                }
                .flowOn(ioDispatcher)
                .collect {
                    pokemonInfo.emit(it)
                }
        }
    }
}
