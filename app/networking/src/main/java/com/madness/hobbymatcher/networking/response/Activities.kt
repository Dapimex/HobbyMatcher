package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class Activities(
    @SerializedName("activities") val activities: List<Activity> = emptyList()
)