package com.expertsclub.expertsauthentication.framework.network

import com.expertsclub.expertsauthentication.data.manager.TokenManager
import com.expertsclub.expertsauthentication.framework.BASE_URL
import com.expertsclub.expertsauthentication.framework.PATH_AUTH
import com.expertsclub.expertsauthentication.framework.PATH_USERS
import com.expertsclub.expertsauthentication.framework.network.interceptor.AuthInterceptor
import com.expertsclub.expertsauthentication.framework.network.response.AuthResponse
import com.expertsclub.expertsauthentication.framework.network.response.UserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST(PATH_AUTH)
    suspend fun login(
        @Field("email")
        email: String,
        @Field("password")
        password: String
    ): AuthResponse

    @GET("$PATH_USERS/{id}")
    suspend fun getUser(@Path("id") id: String): UserResponse

    companion object {
        fun getService(tokenManager: TokenManager): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(tokenManager))
                .build()

            return retrofit.create(ApiService::class.java)
        }

        private fun getOkHttpClient(tokenManager: TokenManager): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(tokenManager))
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .build()
        }
    }
}

