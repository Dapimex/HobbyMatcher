package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("username") val username: String,
    @SerializedName("id") val id: Int,
    @SerializedName("accessToken") val accessToken: String
)