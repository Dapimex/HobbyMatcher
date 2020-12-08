package com.madness.hobbymatcher.networking.request

import com.google.gson.annotations.SerializedName
import java.util.*

enum class ActivityType {
    MOVIE, TABLETOP, WALKING, OTHER
}

data class ActivityCreateRequest(
    @SerializedName("description") val description: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("is_public") val isPublic: Boolean,
    @SerializedName("location") val location: String,
    @SerializedName("start_time") val startTime: Date,
    @SerializedName("type") val type: ActivityType
)
