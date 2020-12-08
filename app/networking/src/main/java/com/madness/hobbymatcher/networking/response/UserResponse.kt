package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName
import com.madness.hobbymatcher.networking.response.ActivityResponse

data class UserResponse(
    @SerializedName("public_activities") val publicActivities: Array<ActivityResponse>,
    @SerializedName("username") val username: String
)