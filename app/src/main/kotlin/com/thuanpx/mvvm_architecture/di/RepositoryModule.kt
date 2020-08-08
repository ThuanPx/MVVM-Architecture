package com.thuanpx.mvvm_architecture.di

import com.thuanpx.mvvm_architecture.data.repository.task.DefaultTaskRepository
import com.thuanpx.mvvm_architecture.data.repository.task.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
    fun provideTaskRepository(): TaskRepository {
        return DefaultTaskRepository()
    }
}
