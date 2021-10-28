package com.expertsclub.expertsauthentication.data.manager

import kotlinx.coroutines.flow.Flow

interface TokenManager {

    suspend fun getAccessToken(): Flow<String>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun clearAccessToken()
}