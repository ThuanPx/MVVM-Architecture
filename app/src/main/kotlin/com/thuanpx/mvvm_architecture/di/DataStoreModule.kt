package com.thuanpx.mvvm_architecture.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.thuanpx.mvvm_architecture.data.local.datastore.PreferenceDataStore
import com.thuanpx.mvvm_architecture.data.local.datastore.PreferenceDataStoreDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by ThuanPx on 4/1/22.
 */

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    private const val DATA_STORE_FILE_NAME = "app_prefs.pb"

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { appContext.preferencesDataStoreFile(DATA_STORE_FILE_NAME) }
        )
    }

    @Singleton
    @Provides
    fun providePreferenceDataStore(dataStore: DataStore<Preferences>): PreferenceDataStore {
        return PreferenceDataStoreDefault(dataStore)
    }
}
