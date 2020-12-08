package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("public_activities") val publicActivities: List<Activity>? = emptyList(),
    @SerializedName("username") val username: String,
    @SerializedName("role") val role: String? = null
)