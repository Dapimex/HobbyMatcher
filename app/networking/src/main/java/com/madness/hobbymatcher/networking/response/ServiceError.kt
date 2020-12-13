package com.madness.hobbymatcher.networking.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import java.util.*

data class ServiceError(
    @SerializedName("timestamp") val time: Date,
    @SerializedName("status") val status: Int,
    @SerializedName("error") val error: String,
    @SerializedName("message") val message: String,
    @SerializedName("path") val path: String
) {
    companion object {
        private val gson = Gson()

        fun from(errorBody: ResponseBody?): ServiceError {
            if (errorBody == null) {
                return ServiceError(Date(), -1, "", "", "")
            }
            return gson.fromJson(errorBody.charStream(), ServiceError::class.java)
        }
    }
}