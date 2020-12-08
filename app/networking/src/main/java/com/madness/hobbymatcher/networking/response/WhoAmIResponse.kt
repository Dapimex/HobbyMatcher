package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class WhoAmIResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String
)
