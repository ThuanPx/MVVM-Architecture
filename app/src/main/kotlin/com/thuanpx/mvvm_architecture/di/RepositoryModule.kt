package com.thuanpx.mvvm_architecture.di

import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.data.repository.DefaultUserRepository
import com.thuanpx.mvvm_architecture.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/7/20.
 */

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): UserRepository {
        return DefaultUserRepository(apiService, coroutineDispatcher)
    }
}
