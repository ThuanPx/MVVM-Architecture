package com.thuanpx.mvvm_architecture.ui.pokemonDetail

import com.thuanpx.mvvm_architecture.base.BaseViewModel
import com.thuanpx.mvvm_architecture.data.repository.PokemonRepository
import com.thuanpx.mvvm_architecture.model.entity.PokemonInfo
import com.thuanpx.mvvm_architecture.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/16/20.
 */
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    val pokemonInfo = SingleLiveData<PokemonInfo>()

    fun fetchPokemon(name: String) {
        viewModelScope(pokemonInfo) {
            pokemonRepository.fetchPokemon(name)
        }
    }
}
