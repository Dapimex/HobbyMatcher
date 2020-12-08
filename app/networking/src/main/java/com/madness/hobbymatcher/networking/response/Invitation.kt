package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class Invitation(
    @SerializedName("activity_id") val activityId: Int,
    @SerializedName("activity_name") val activityName: String? = "",
    @SerializedName("creation_time") val creationTime: String? = "",
    @SerializedName("id") val id: Int? = null,
    @SerializedName("sender_id") val senderId: Int? = null,
    @SerializedName("sender_username") val senderUsername: String? = "",
    @SerializedName("target_user_id") val targetUserId: Int? = null
)
