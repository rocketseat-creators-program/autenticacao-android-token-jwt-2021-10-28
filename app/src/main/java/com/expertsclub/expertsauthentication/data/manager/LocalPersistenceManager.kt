package com.expertsclub.expertsauthentication.data.manager

import kotlinx.coroutines.flow.Flow

interface LocalPersistenceManager {

    suspend fun getUserId(): Flow<String>
    suspend fun saveUserId(id: String)
    suspend fun clearUser()
}