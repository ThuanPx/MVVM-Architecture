package com.thuanpx.mvvm_architecture.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.thuanpx.ktext.string.nullToEmpty
import com.thuanpx.mvvm_architecture.data.local.datastore.PreferenceDataStoreDefault.PreferencesKeys.PREF_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Copyright © 2022 Est Rouge VN.
 * Created by ThuanPx on 4/1/22.
 */

interface PreferenceDataStore {
    suspend fun token(token: String)
    val token: Flow<String>

}

@Singleton
class PreferenceDataStoreDefault @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataStore {

    object PreferencesKeys {
        val PREF_TOKEN = stringPreferencesKey("pref_token")
    }

    override suspend fun token(token: String) {
        dataStore.edit { it[PREF_TOKEN] = token }
    }

    override val token: Flow<String> = dataStore.data.map { it[PREF_TOKEN].nullToEmpty() }
}
