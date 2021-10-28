package com.expertsclub.expertsauthentication.framework.network.response

import com.expertsclub.expertsauthentication.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
)

fun UserResponse.toUserDomain() = User(
    id = this.id.toString(),
    displayName = this.name
)
