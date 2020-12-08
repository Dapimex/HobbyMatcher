package com.madness.hobbymatcher.networking.request

import com.google.gson.annotations.SerializedName

data class InvitationCreateRequest(
    @SerializedName("activity_id") val acivityId: Int,
    @SerializedName("target_user_id") val targetUserId: Int
)
