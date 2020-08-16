package com.thuanpx.mvvm_architecture.data.repository

import com.thuanpx.mvvm_architecture.common.base.BaseRepository
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.di.IoDispatcher
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.model.entity.PokemonInfo
import com.thuanpx.mvvm_architecture.utils.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/13/20.
 */

interface PokemonRepository {
    suspend fun fetchPokemons(page: Int): DataResult<List<Pokemon>>
    suspend fun fetchPokemon(name: String): DataResult<PokemonInfo>
}

class DefaultPokemonRepository @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : PokemonRepository, BaseRepository(coroutineDispatcher) {

    override suspend fun fetchPokemons(page: Int): DataResult<List<Pokemon>> {
        return withResultContext { apiService.fetchPokemons(page = page).data }
    }

    override suspend fun fetchPokemon(name: String): DataResult<PokemonInfo> {
        return withResultContext { apiService.fetchPokemon(name) }
    }
}
