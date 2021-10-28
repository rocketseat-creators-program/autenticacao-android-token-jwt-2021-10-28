package com.expertsclub.expertsauthentication.data.repository

import com.expertsclub.expertsauthentication.data.manager.LocalPersistenceManager
import com.expertsclub.expertsauthentication.framework.network.response.UserResponse
import kotlinx.coroutines.flow.first

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localPersistenceManager: LocalPersistenceManager
) {

    suspend fun saveUserId(id: String) {
        localPersistenceManager.saveUserId(id)
    }

    suspend fun clearUser() {
        localPersistenceManager.clearUser()
    }

    suspend fun isUserLoggedIn() = localPersistenceManager.getUserId().first().isNotEmpty()

    suspend fun getUser(): UserResponse {
        val userId = localPersistenceManager.getUserId().first()
        return remoteDataSource.getUser(userId)
    }
}