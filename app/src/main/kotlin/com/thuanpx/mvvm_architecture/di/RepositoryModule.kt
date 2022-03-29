package com.thuanpx.mvvm_architecture.di

import android.app.Application
import com.thuanpx.mvvm_architecture.data.local.sharedpfers.SharedPrefsImpl
import com.thuanpx.mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.mvvm_architecture.data.repository.AppRepository
import com.thuanpx.mvvm_architecture.data.repository.DefaultAppRepository
import com.thuanpx.mvvm_architecture.data.repository.DefaultSharedPrefsRepository
import com.thuanpx.mvvm_architecture.data.repository.SharedPrefsRepository
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
    fun provideSharedPrefsRepository(
        application: Application
    ): SharedPrefsRepository {
        return DefaultSharedPrefsRepository(SharedPrefsImpl(application))
    }

    @Singleton
    @Provides
    fun provideAppRepository(apiService: ApiService, @IoDispatcher ioDispatcher: CoroutineDispatcher): AppRepository {
        return DefaultAppRepository(apiService, ioDispatcher)
    }
}
