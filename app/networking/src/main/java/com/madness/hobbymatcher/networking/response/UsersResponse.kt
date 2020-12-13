package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("users") val users: List<User> = emptyList()
)