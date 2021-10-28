package com.expertsclub.expertsauthentication.framework.network.manager

import com.expertsclub.expertsauthentication.data.manager.TokenManager
import com.expertsclub.expertsauthentication.data.repository.PreferencesDataSource
import com.expertsclub.expertsauthentication.framework.PREF_KEY_ACCESS_TOKEN
import kotlinx.coroutines.flow.Flow

class TokenManagerImpl(private val preferencesDataSource: PreferencesDataSource) : TokenManager {

    override suspend fun getAccessToken(): Flow<String> {
        return preferencesDataSource.getFlow(PREF_KEY_ACCESS_TOKEN)
    }

    override suspend fun saveAccessToken(accessToken: String) {
        preferencesDataSource.save(PREF_KEY_ACCESS_TOKEN, accessToken)
    }

    override suspend fun clearAccessToken() {
        preferencesDataSource.clearAll()
    }
}