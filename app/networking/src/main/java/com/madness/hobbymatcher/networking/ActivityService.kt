package com.madness.hobbymatcher.networking

import com.madness.hobbymatcher.networking.response.Activities
import com.madness.hobbymatcher.networking.response.Activity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ActivityService {
    @GET("activities")
    fun getVisibleActivities(
    ): Call<Activities>

    @POST("activities")
    fun createActivity(
        @Body activity: Activity
    ): Call<Activity>

    @GET("activities/{id}")
    fun getActivity(
        @Path("id") id: Int
    ): Call<Activity>

    @DELETE("activities/{id}")
    fun deleteActivity(
        @Path("id") id: Int
    ): Call<ResponseBody>

    @POST("activities/{id}/join")
    fun joinActivity(
        @Path("id") id: Int
    ): Call<ResponseBody>

    @GET("activities/created")
    fun getMyActivities(): Call<Activities>

    @GET("activities/joined")
    fun getJoinedActivities(): Call<Activities>
}
