package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("username") val username: String? = "",
    @SerializedName("accessToken") val accessToken: String? = "",
    @SerializedName("id") val id: Long? = null
)