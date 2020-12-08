package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class Activity(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String? = "",
    @SerializedName("start_time") val startTime: String? = "",
    @SerializedName("duration") val duration: String? = "",
    @SerializedName("is_public") val isPublic: Boolean? = null,
    @SerializedName("type") val type: String? = "",
    @SerializedName("participants") val participants: List<User>? = emptyList(),
)