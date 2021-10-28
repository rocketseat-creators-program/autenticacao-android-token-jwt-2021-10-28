package com.expertsclub.expertsauthentication.framework.network.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token")
    val accessToken: String
)
