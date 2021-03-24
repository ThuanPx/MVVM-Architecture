package com.thuanpx.mvvm_architecture.ui.home

import com.thuanpx.mvvm_architecture.base.BaseViewModel
import com.thuanpx.mvvm_architecture.data.repository.PokemonRepository
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    val pokemons = SingleLiveData<List<Pokemon>>()

    fun fetchPokemons(page: Int = 0) {
        viewModelScope(pokemons) {
            pokemonRepository.fetchPokemons(page)
        }
    }
}
