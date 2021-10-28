package com.expertsclub.expertsauthentication.framework.preferences.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.expertsclub.expertsauthentication.data.repository.PreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataSourceImpl(private val dataStore: DataStore<Preferences>) :
    PreferencesDataSource {

    override suspend fun getFlow(prefKey: String): Flow<String> {
        val stringPrefKey = stringPreferencesKey(prefKey)
        return dataStore.data.map { preferences ->
            preferences[stringPrefKey] ?: ""
        }
    }

    override suspend fun save(prefKey: String, prefValue: String) {
        val stringPrefKey = stringPreferencesKey(prefKey)
        dataStore.edit { preferences ->
            preferences[stringPrefKey] = prefValue
        }
    }

    override suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}