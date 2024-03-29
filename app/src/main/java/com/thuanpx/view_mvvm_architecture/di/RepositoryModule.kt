package com.thuanpx.view_mvvm_architecture.di

import com.thuanpx.view_mvvm_architecture.data.remote.api.ApiService
import com.thuanpx.view_mvvm_architecture.data.repository.AppRepository
import com.thuanpx.view_mvvm_architecture.data.repository.DefaultAppRepository
import com.thuanpx.view_mvvm_architecture.di.AppDispatchers
import com.thuanpx.view_mvvm_architecture.di.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Created by ThuanPx on 8/7/20.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAppRepository(apiService: ApiService, @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher): AppRepository {
        return DefaultAppRepository(apiService, ioDispatcher)
    }
}
