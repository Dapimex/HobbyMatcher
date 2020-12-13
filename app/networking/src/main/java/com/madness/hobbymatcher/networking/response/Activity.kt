package com.madness.hobbymatcher.networking.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Activity(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String? = "",
    @SerializedName("start_time") val startTime: String? = "",
    @SerializedName("duration") val duration: String? = "",
    @SerializedName("location") val location: String? = "",
    @SerializedName("is_public") val isPublic: Boolean? = null,
    @SerializedName("type") val type: String? = "",
    @SerializedName("participants") val participants: List<User>? = emptyList(),
) : Parcelable