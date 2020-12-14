package com.madness.hobbymatcher.networking.response

import com.google.gson.annotations.SerializedName

data class Invitations(
    @SerializedName("invitations") val invitations: List<Invitation> = emptyList()
)