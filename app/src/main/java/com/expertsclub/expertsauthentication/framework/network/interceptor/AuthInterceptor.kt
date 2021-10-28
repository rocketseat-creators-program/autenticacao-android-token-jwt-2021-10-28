package com.expertsclub.expertsauthentication.framework.network.interceptor

import com.expertsclub.expertsauthentication.data.manager.TokenManager
import com.expertsclub.expertsauthentication.data.repository.PreferencesDataSource
import com.expertsclub.expertsauthentication.framework.HEADER_AUTHORIZATION
import com.expertsclub.expertsauthentication.framework.HEADER_BEARER
import com.expertsclub.expertsauthentication.framework.PATH_AUTH
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        val newRequest = if (!url.pathSegments.contains(PATH_AUTH)) {
            val accessToken = runBlocking { tokenManager.getAccessToken().first() }

            request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$HEADER_BEARER $accessToken")
                .build()
        } else request

        return chain.proceed(newRequest)
    }
}