package com.thuanpx.mvvm_architecture.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thuanpx.mvvm_architecture.base.BaseRepository
import com.thuanpx.mvvm_architecture.data.remote.datasource.PokemonDataSource
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.di.IoDispatcher
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.model.response.BaseResponse
import com.thuanpx.mvvm_architecture.utils.coroutines.suspendOnSuccessAutoError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 17/09/2021.
 */

interface AppRepository {
    suspend fun fetchPokemonList(page: Int): Flow<BaseResponse<List<Pokemon>>>
    fun fetchPokemon2(isLoading: MutableLiveData<Boolean>): Flow<PagingData<Pokemon>>
}

class DefaultAppRepository @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository, BaseRepository() {

    override suspend fun fetchPokemonList(page: Int): Flow<BaseResponse<List<Pokemon>>> {
        return flow {
            apiService.fetchPokemons(page = page)
                .suspendOnSuccessAutoError {
                    emit(this.data)
                }
        }
    }

    override fun fetchPokemon2(isLoading: MutableLiveData<Boolean>): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { PokemonDataSource(apiService, isLoading) }
        ).flow
    }
}
