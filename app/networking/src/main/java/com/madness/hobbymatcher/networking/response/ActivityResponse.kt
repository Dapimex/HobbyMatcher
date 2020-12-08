package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class ActivityResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("start_time") val startTime: Date
)