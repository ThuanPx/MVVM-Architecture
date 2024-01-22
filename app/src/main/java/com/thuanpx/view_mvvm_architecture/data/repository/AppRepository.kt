package com.thuanpx.view_mvvm_architecture.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thuanpx.view_mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.view_mvvm_architecture.data.remote.datasource.PokemonDataSource
import com.thuanpx.view_mvvm_architecture.di.AppDispatchers
import com.thuanpx.view_mvvm_architecture.di.Dispatcher
import com.thuanpx.view_mvvm_architecture.model.entity.Pokemon
import com.thuanpx.view_mvvm_architecture.model.entity.PokemonInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by ThuanPx on 17/09/2021.
 */

interface AppRepository {
    fun fetchPokemon(): Flow<PagingData<Pokemon>>
    fun fetchPokemonInfo(query: String): Flow<PokemonInfo>
}

class DefaultAppRepository @Inject constructor(
    private val apiService: ApiService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : AppRepository {

    override fun fetchPokemon(): Flow<PagingData<Pokemon>> {
        return Pager(config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { PokemonDataSource(apiService) }).flow
    }

    override fun fetchPokemonInfo(query: String): Flow<PokemonInfo> {
        return flow { emit(apiService.fetchPokemon(query)) }
            .flowOn(ioDispatcher)
    }
}
