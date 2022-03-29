package com.thuanpx.mvvm_architecture.data.repository

import com.thuanpx.mvvm_architecture.base.BaseRepository
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.di.IoDispatcher
import com.thuanpx.mvvm_architecture.model.entity.Pokemon
import com.thuanpx.mvvm_architecture.model.response.BaseResponse
import com.thuanpx.mvvm_architecture.utils.coroutines.suspendOnSuccessAndError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 17/09/2021.
 */

interface AppRepository {
    suspend fun fetchPokemonList(page: Int): Flow<BaseResponse<List<Pokemon>>>
}

class DefaultAppRepository @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository, BaseRepository() {

    override suspend fun fetchPokemonList(page: Int): Flow<BaseResponse<List<Pokemon>>> {
        return flow {
            apiService.fetchPokemons(page = page)
                .suspendOnSuccessAndError {
                    emit(this.data)
                }
        }
    }
}
