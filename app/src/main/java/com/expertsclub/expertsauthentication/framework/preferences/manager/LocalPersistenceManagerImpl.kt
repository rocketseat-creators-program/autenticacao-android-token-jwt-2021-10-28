package com.expertsclub.expertsauthentication.framework.preferences.manager

import com.expertsclub.expertsauthentication.data.manager.LocalPersistenceManager
import com.expertsclub.expertsauthentication.data.repository.PreferencesDataSource
import com.expertsclub.expertsauthentication.framework.PREF_KEY_USER_ID
import kotlinx.coroutines.flow.Flow

class LocalPersistenceManagerImpl(
    private val preferencesDataSource: PreferencesDataSource
) : LocalPersistenceManager {

    override suspend fun getUserId(): Flow<String> {
        return preferencesDataSource.getFlow(PREF_KEY_USER_ID)
    }

    override suspend fun saveUserId(id: String) {
        preferencesDataSource.save(PREF_KEY_USER_ID, id)
    }

    override suspend fun clearUser() {
        preferencesDataSource.clearAll()
    }
}