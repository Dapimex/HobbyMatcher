package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class InvitationResponse(
    @SerializedName("activity_id") val activityId: Int,
    @SerializedName("activity_name") val activityName: String,
    @SerializedName("creation_time") val creationTime: Date,
    @SerializedName("id") val id: Int,
    @SerializedName("sender_id") val senderId: Int,
    @SerializedName("sender_username") val senderUsername: String
)
