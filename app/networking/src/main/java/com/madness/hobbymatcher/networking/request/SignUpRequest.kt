package com.madness.hobbymatcher.networking.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("username") val username: String? = "",
    @SerializedName("password") val password: String? = "",
)