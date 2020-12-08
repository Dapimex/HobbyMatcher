package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class SignUp(
    @SerializedName("username") val username: String? = "",
    @SerializedName("id") val id: Int? = null,
    @SerializedName("accessToken") val accessToken: String? = ""
)