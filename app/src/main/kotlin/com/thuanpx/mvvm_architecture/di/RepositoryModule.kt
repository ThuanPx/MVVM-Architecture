package com.thuanpx.mvvm_architecture.di

import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.data.repository.DefaultPokemonRepository
import com.thuanpx.mvvm_architecture.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/7/20.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        apiService: ApiService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): PokemonRepository {
        return DefaultPokemonRepository(apiService, coroutineDispatcher)
    }
}
