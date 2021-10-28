package com.expertsclub.expertsauthentication.data.repository

import com.expertsclub.expertsauthentication.framework.network.response.AuthResponse
import com.expertsclub.expertsauthentication.framework.network.response.UserResponse

interface RemoteDataSource {

    suspend fun login(email: String, password: String): AuthResponse

    suspend fun getUser(id: String): UserResponse
}