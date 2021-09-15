package com.thuanpx.mvvm_architecture.di

import android.app.Application
import com.thuanpx.mvvm_architecture.data.local.sharedpfers.SharedPrefsImpl
import com.thuanpx.mvvm_architecture.data.repository.DefaultSharedPrefsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    ): DefaultSharedPrefsRepository {
        return DefaultSharedPrefsRepository(SharedPrefsImpl(application))
    }
}
