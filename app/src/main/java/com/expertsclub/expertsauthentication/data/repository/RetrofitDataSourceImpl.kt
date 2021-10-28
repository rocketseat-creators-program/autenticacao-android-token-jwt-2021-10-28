package com.expertsclub.expertsauthentication.data.repository

import com.expertsclub.expertsauthentication.framework.network.ApiService
import com.expertsclub.expertsauthentication.framework.network.response.AuthResponse
import com.expertsclub.expertsauthentication.framework.network.response.UserResponse

class RetrofitDataSourceImpl(private val apiService: ApiService) : RemoteDataSource {

    override suspend fun login(email: String, password: String): AuthResponse {
        return apiService.login(email, password)
    }

    override suspend fun getUser(id: String): UserResponse {
        return apiService.getUser(id)
    }
}