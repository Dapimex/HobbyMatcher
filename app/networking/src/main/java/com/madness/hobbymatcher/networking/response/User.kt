package com.madness.hobbymatcher.networking.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("public_activities") val publicActivities: List<Activity>? = emptyList(),
    @SerializedName("username") val username: String,
    @SerializedName("role") val role: String? = null,
    @SerializedName("id") val id: Int? = null
) : Parcelable