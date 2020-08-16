package com.thuanpx.mvvm_architecture.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import com.thuanpx.mvvm_architecture.common.base.BaseViewModel
import com.thuanpx.mvvm_architecture.data.repository.PokemonRepository
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/8/20.
 */
class HomeViewModel @ViewModelInject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    val pokemons = SingleLiveData<List<Pokemon>>()

    fun fetchPokemons(page: Int = 0) {
        viewModelScope(pokemons) {
            pokemonRepository.fetchPokemons(page)
        }
    }
}
