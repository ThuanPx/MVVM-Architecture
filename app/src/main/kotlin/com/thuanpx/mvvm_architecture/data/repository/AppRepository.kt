package com.thuanpx.mvvm_architecture.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.data.remote.datasource.PokemonDataSource
import com.thuanpx.mvvm_architecture.di.IoDispatcher
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.model.entity.PokemonInfo
import com.thuanpx.mvvm_architecture.utils.coroutines.dataOrException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 17/09/2021.
 */

interface AppRepository {
    fun fetchPokemon(isLoading: MutableLiveData<Boolean>): Flow<PagingData<Pokemon>>
    fun fetchPokemonInfo(query: String): Flow<PokemonInfo>
}

class DefaultAppRepository @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository {

    override fun fetchPokemon(isLoading: MutableLiveData<Boolean>): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { PokemonDataSource(apiService, isLoading) }
        ).flow
    }

    override fun fetchPokemonInfo(query: String): Flow<PokemonInfo> {
        return flow { emit(apiService.fetchPokemon(query).dataOrException()) }
    }
}
