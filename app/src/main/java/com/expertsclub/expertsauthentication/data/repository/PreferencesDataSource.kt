package com.expertsclub.expertsauthentication.data.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {

    suspend fun getFlow(prefKey: String): Flow<String>

    suspend fun save(prefKey: String, prefValue: String)

    suspend fun clearAll()
}