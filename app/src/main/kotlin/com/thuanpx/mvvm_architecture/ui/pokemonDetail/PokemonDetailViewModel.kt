package com.thuanpx.mvvm_architecture.ui.pokemonDetail

import androidx.hilt.lifecycle.ViewModelInject
import com.thuanpx.mvvm_architecture.common.base.BaseViewModel
import com.thuanpx.mvvm_architecture.data.repository.PokemonRepository
import com.thuanpx.mvvm_architecture.model.entity.PokemonInfo
import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/16/20.
 */
class PokemonDetailViewModel @ViewModelInject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    val pokemonInfo = SingleLiveData<PokemonInfo>()

    fun fetchPokemon(name: String) {
        viewModelScope(pokemonInfo) {
            pokemonRepository.fetchPokemon(name)
        }
    }
}
