package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class SignIn(
    @SerializedName("username") val username: String? = "",
    @SerializedName("accessToken") val accessToken: String? = ""
)