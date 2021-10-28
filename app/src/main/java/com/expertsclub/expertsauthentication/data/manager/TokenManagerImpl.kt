package com.expertsclub.expertsauthentication.data.manager

import com.expertsclub.expertsauthentication.data.repository.PreferencesDataSource
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

    companion object {
        private const val PREF_KEY_ACCESS_TOKEN = "prefKeyAccessToken"
    }
}