package com.expertsclub.expertsauthentication.data.repository

import com.expertsclub.expertsauthentication.data.manager.TokenManager
import com.expertsclub.expertsauthentication.framework.network.response.AuthResponse

class AuthRepository(
    private val remoteDataSource: RemoteDataSource,
    private val tokenManager: TokenManager
) {

    suspend fun login(email: String, password: String): AuthResponse {
        return remoteDataSource.login(email, password)
    }

    suspend fun saveAccessToken(token: String) {
        tokenManager.saveAccessToken(token)
    }

    suspend fun clearAccessToken() {
        tokenManager.clearAccessToken()
    }
}